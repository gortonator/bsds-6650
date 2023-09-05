# CS6650 Spring 2023  Assignment 3

COMING SOON

##### Addendum - DynamoDB

**For those interested inDynamoDB**

If you are interested in using DynamoDB, here are some hopefully useful resources:

1. A series of documentation explaining how to set up confidential before you could make requests to AWS using AWS SDK for Java 2.X.  
   https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/credentials.html.  
   I have already verified the first two options work well in this [section](https://nam12.safelinks.protection.outlook.com/?url=https%3A%2F%2Fdocs.aws.amazon.com%2Fsdk-for-java%2Flatest%2Fdeveloper-guide%2Fcredentials-chain.html&data=05%7C01%7Ci.gorton%40northeastern.edu%7Cefdb3826568247aa065008db28ec6227%7Ca8eec281aaa34daeac9b9a398b9215e7%7C0%7C0%7C638148767954694150%7CUnknown%7CTWFpbGZsb3d8eyJWIjoiMC4wLjAwMDAiLCJQIjoiV2luMzIiLCJBTiI6Ik1haWwiLCJXVCI6Mn0%3D%7C3000%7C%7C%7C&sdata=Ul2Fq9eVxt0UogRJ26m%2BbbnrLU8sN9r6LruwHR%2Flny0%3D&reserved=0).  
   The credentials(aws_access_key_id, aws_secret_access_key, aws_session_token) could be found on your learner's lab page by clicking the right top corner "aws details"
2. Examples of interacting with DynamoDB using AWS SDK for Java 2.X.  
   https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/GettingStarted.html  
   I also found the API reference included in each example really helpful.

DynamoDBPricing  
https://aws.amazon.com/dynamodb/pricing/  
Pay attention to the difference between “provisioned capacity mode” and“on-demand capacity mode”.  
As to how to set billing mode in code, check link below https://sdk.amazonaws.com/java/api/latest/software/amazon/awssdk/services/dynamodb/model/CreateTableRequest.html#billingMode()Version:1.0StartHTML:0000000105EndHTML:0000042057StartFragment:0000039335EndFragment:0000042017<style></style>
