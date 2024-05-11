package com.ypay.money.query.adapter.out.aws.dynamodb;

import com.ypay.money.query.adapter.axon.QueryMoneySumByAddress;
import com.ypay.money.query.application.port.out.GetMoneySumByRegionPort;
import com.ypay.money.query.application.port.out.InsertMoneyIncreaseEventByAddress;
import com.ypay.money.query.application.port.out.MoneySum;
import com.ypay.money.query.domain.MoneySumByRegion;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import static org.springframework.http.RequestEntity.put;

@Component
public class DynamoDBAdapter implements GetMoneySumByRegionPort, InsertMoneyIncreaseEventByAddress {
    private static final String TABLE_NAME = "MoneyIncreaseEventByRegion";

    @Value("AWS_ACCESS_KEY")
    private static String ACCESS_KEY;

    @Value("AWS_SECRET_KEY")
    private static String SECRET_KEY;

    private final DynamoDbClient dynamoDbClient;

    private final MoneySumByAddressMapper moneySumByAddressMapper;

    public DynamoDBAdapter() {
        this.moneySumByAddressMapper = new MoneySumByAddressMapper();
        AwsBasicCredentials credentials = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);
        this.dynamoDbClient = DynamoDbClient.builder()
                .region(Region.AP_NORTHEAST_2)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    @Override
    public MoneySum getMoneySumByRegionPort(String regionName, Date startDate) {

        return null;
    }


    @Override
    public void insertMoneyIncreaseEventByAddress(String addressName, int moneyIncrease) {
        //3가지의 일 을 해야한다.

        // 1. raw event insert (Insert, put)
        // PK: 강남구#230728 SK: 5,000

        String pk = addressName + "#" + new DateFormatter("yyMMdd").print(new Date(), null);
        String sk = String.valueOf(moneyIncrease);

        putItem(pk, sk, moneyIncrease);
        // 2. 지역 정보 잔액 증가시켜야 한다. (Query, Update)
        // 2-1. 지역별/일별 정보
        // - PK: 강남구#230728#summary SK: -1 balance: + 5,000

        String summaryPk = pk + "summary";
        String summarySk = "-1";
        MoneySumByAddress moneySumByAddress = getItem(summaryPk, summarySk);

        int balance = moneySumByAddress.getBalance();
        balance += moneyIncrease;
        putItem(summaryPk, summarySk, balance);

        // 2-2 지역별 정보
        // - PK: 강남구 SK: -1 balance: + 5,000

        String summaryPk2 = addressName;
        String summarySk2 = "-1";
        MoneySumByAddress moneySumByAddress2 = getItem(summaryPk2, summarySk2);

        int balance2 = moneySumByAddress2.getBalance();
        balance2 += moneyIncrease;
        putItem(summaryPk2, summarySk2, balance2);



    }

    private void putItem(String pk, String sk, int balance) {
        try {
            HashMap<String, AttributeValue> attrMap = new HashMap<>();
            attrMap.put("PK", AttributeValue.builder().s(pk).build());
            attrMap.put("SK", AttributeValue.builder().s(sk).build());
            attrMap.put("balance", AttributeValue.builder().n(String.valueOf(balance)).build());

            PutItemRequest request = PutItemRequest.builder()
                    .tableName(TABLE_NAME)
                    .item(attrMap)
                    .build();

            dynamoDbClient.putItem(request);
        } catch (DynamoDbException e) {
            System.err.println("Error adding an item to the table: " + e.getMessage());
        }
    }


    private MoneySumByAddress getItem(String pk, String sk) {
        try {
            HashMap<String, AttributeValue> attrMap = new HashMap<>();
            attrMap.put("PK", AttributeValue.builder().s(pk).build());
            attrMap.put("SK", AttributeValue.builder().s(sk).build());


            GetItemRequest request = GetItemRequest.builder()
                    .tableName(TABLE_NAME)
                    .key(attrMap)
                    .build();

            GetItemResponse response = dynamoDbClient.getItem(request);
            response.item().forEach((key, value) -> System.out.println(key + ": " + value));

            return moneySumByAddressMapper.mapToMoneySumByAddress(response.item());
        } catch (DynamoDbException e) {
            System.err.println("Error getting an item from the table: " + e.getMessage());
        }

        return null;
    }

    private void queryItem(String id) {
        try {
            HashMap<String, Condition> attrMap = new HashMap<>();
            attrMap.put("PK", Condition.builder()
                    .attributeValueList(AttributeValue.builder().s(id).build())
                    .comparisonOperator(ComparisonOperator.EQ)
                    .build());

            QueryRequest request = QueryRequest.builder()
                    .tableName(TABLE_NAME)
                    .keyConditions(attrMap)
                    .build();

            QueryResponse response = dynamoDbClient.query(request);
            response.items().forEach((value) -> System.out.println(value));
        } catch (DynamoDbException e) {
            System.err.println("Error getting an item from the table: " + e.getMessage());
        }
    }

    private void updateItem(String pk, String sk, int balance) {
        try {
            HashMap<String, AttributeValue> attrMap = new HashMap<>();
            attrMap.put("PK", AttributeValue.builder().s(pk).build());
            attrMap.put("SK", AttributeValue.builder().s(sk).build());


            UpdateItemRequest updateItemRequest = UpdateItemRequest.builder()
                    .tableName(TABLE_NAME)
                    .key(attrMap)
                    .attributeUpdates(
                        new HashMap<String, AttributeValueUpdate>(){{
                            put("balance", AttributeValueUpdate.builder()
                                    .value(AttributeValue.builder().n(String.valueOf(balance)).build())
                                    .action(AttributeAction.PUT)
                                    .build());
                        }}
                    )
                    .build();

            UpdateItemResponse updateItemResponse = dynamoDbClient.updateItem(updateItemRequest);

        } catch (DynamoDbException e) {
            System.err.println("Error getting an item from the table: " + e.getMessage());
        }
    }

    @QueryHandler
    public MoneySumByRegion query (QueryMoneySumByAddress query){
        return MoneySumByRegion.generateMoneySumByRegion(
                new MoneySumByRegion.MoneySumByRegionId(UUID.randomUUID().toString()),
                new MoneySumByRegion.RegionName(query.getAddress()),
                new MoneySumByRegion.MoneySum(1000)
        );
    }
}
