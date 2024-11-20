
Loyalty Reward System
=====================================


This is a wallet topup and deduction SDK used in loyalty reward programs by various B2C businesses. It involves credit and debit of reward points or amount that comes with a fixed expiry time for each credit. Some of the popular examples of application of such a system is Paytm wallet, Flipkart Supercoins, Credit card reward points, Online wallets, Amazonpay balance etc.

## Description

This is the implementation of online wallet payment system where topup and deduction transactions will be done. It also comes with an expiry time after which the respective topup amount (if not utilized) will be expired. The solution has been implemented using Java - Spring Boot using microservice architecture. The solution involves Sign up, Login, Email verification, Credit-debit the points and capture the transactions accordingly.

## Microservices

* User Service - It captures the user data, also provides roles for the users.
* Admin Service - It captures session history data, generates secrets for Admin users and performs admin related operations.
* Transaction Service - It involves credit and debit, getting the passbook of all the transactions.
* API Gateway Service - This is the service which acts as the API gateway to route the requests
* Service Registry - Implemented using Eureka, which holds the services' data
* Email Service - It involves generation of OTP and sending emails to the users.

## Technical specifications

* Authentication - Spring Security, JWT Authentication using Spring webflux (Reactive)
* Cache - Redis Cache as a distributed caching mechanism
* Synchronous communication between microservices - Feign Client 
* Messaging platform - Apache Kafka
* API Requests routing - Spring cloud gateway & Eureka service Registry
* Logging framework - ELK logging (Elastic, LogStash, Kibana)
* Email - Email service with OTP implementation
* Database - MySQL (separate databases for each microservice)
* Jobs - Scheduled cronjobs for cleanup and time bound activities
* API framework - REST


## APIs covered:
* Sign Up - User registration, sends an email with OTP to the user
* Resend email - Triggers another email with new OTP and expires the old OTP
* Verify OTP - Validates the OTP
* Login - Login validation & generation of JWT token
* Generate Admin Secret - It generates a secret which needs to be inputted at the time of signup if it needs to be an admin user, will be validated at the time of Sign Up.
* Topup - It adds balance value to the user
* Deduct - It deducts balance from the user. It starts deduction from the oldest topups.
* Balance - Current balance amount
* Passbook - Gives all topup, deduct and expiry transaction details.
* Get User, Get Transaction - APIs to fetch the respective elements 
* Get all login sessions by ID - ADMIN ONLY - Gives details of history of all login sessions of a particular user 
* Get all signup sessions by ID - ADMIN ONLY - Gives details of history of signup sessions of a particular user
* Few internal APIs which are only for developer purposes.

## API Documentation

[Click here to view the API Documentation](https://documenter.getpostman.com/view/18403833/2sA3dygWA1)

## Data modeling - Entity Relationship Diagram

* Admin Service - [Admin Service ER Diagram](https://viewer.diagrams.net/?tags=%7B%7D&lightbox=1&target=blank&highlight=0000ff&edit=_blank&layers=1&nav=1&title=Admin%20Service.drawio#R%3Cmxfile%3E%3Cdiagram%20id%3D%22R2lEEEUBdFMjLlhIrx00%22%20name%3D%22Page-1%22%3E7Vpbj9o8EP01edxVLsDuPgLLfq3ESqumVR8rNzGJhZNJHXPrr%2B%2FYcW4b6AfaZUFqJITw8dgez5yZHASWN022%2FwmSxc8QUm65dri1vEfLde8eBviugF0BDBy3ACLBwgJyasBnv6kBbYOuWEjzlqEE4JJlbTCANKWBbGFECNi0zRbA26dmJKIdwA8I76LfWSjjAr0f2jX%2BibIoLk92bDOTkNLYAHlMQti0ILqVT5BK4%2BILFQlJaSpx5pmIJRXWcBZLqW46ttwnfC2U9W0EEHFKMpbfBpAgHORo8rQgCeMqzI2NJmYjPM6bWd5UAMjiU7KdUq5SVaah8OnpwGwVB6H2PWLBeOb9ePj0RfizwXLEMrj54axvyqyuCV%2BZAI%2FDhKUI%2BTQQFENRxEruygTkG5ZwvI3lTdTtfTNj4ziIGQ%2FnZAcr5VEuSbAsR5MYBPuN9oTjlIMATgtp%2BOXZLQtfrTR7CpqjzUt5TecV9Ey2LcM5yWXpDXBOspz91P6phQkREUsnICVmqTDaxExSPyOBstlgxShHZFI6KUASySA11uXd5ywt96xYpA%2BANamP0x41x2qzxhgrkzbHNGTNIYdgScPqMrqcGtMmcVRIuj1ICaciGvYDCgmVYocmZoE3MOk3vcAZmfGmriznwWBxs6ruDCgox%2Fis27VJTP1E1YGVDy%2FAdDmVDrw6v6zicgdYLHIqm7TGD42b1JAm%2BynEdzrE%2F4xvtq5s25eCpVGH%2BhhpqZkrYEmnwEEgnkJRC4zzVxDhLFLM4XShlqlUMWxkYwMnLAzVzpMc6YfHzbXZ46BGvpiIKwhw%2BYJrpsW4kOIOk0xFU4dqOMEXBm9q3w6tIfo6xbFTj%2FGlzIWcQoruE6YJQrFYNlQVTIubp9fFUVz8SwPqMnTXJsj%2FEdKzD1OvRZ2TeeJ2eFI0xg5XRlzFMWRr%2FBhJHZQC%2BileI%2BjJHrsG9Bnbrv0tx%2BovT5ngM5aS9Ogd3nD4VNAit679SCStXFCDryyhH%2BGDbuP41IWQLZgOw8VcOX5p3y0u3C2Ofnw5Z2sXg067mENk9FSeYwz%2BcUHVkkvvIGGG7gEJ0eCAa%2B%2FhgHt%2FNg4Me2nxhmbxhu4wOFVL7GXG%2BbTEqEMMfMbjY8Xu%2BXEF%2FNjz9Phgftx1%2BDFLCONnlZovJM83IMIDh5xTWWmVOw7098cL6NyvWEnpBa79b%2Bvrvq99dF97OLKvVVL53Rub21XFPiZslfWy%2BEyy2L27Olns9rL4Mu3BvXJZ7Pay%2BJr5cXFZ7PayuJfFvSzu%2B9r79rUrkMX3ncZW0kMp3hYjRr9WUE7c5FrNKkZiGLYFq8x8yaj6V3yxZkGTscXWB3gnYJWG1Y%2FO75Gav%2BvUKrgmKXtk6mBfUkan5wSH9Z8tip%2BR6z%2FIeLM%2F%3C%2Fdiagram%3E%3C%2Fmxfile%3E)

* User Service - [User Service ER Diagram](https://viewer.diagrams.net/?tags=%7B%7D&lightbox=1&highlight=0000ff&edit=_blank&layers=1&nav=1&title=User%20Service.drawio#R%3Cmxfile%3E%3Cdiagram%20name%3D%22Page-1%22%20id%3D%22ZyK53TdAnlDr-O_lH7QT%22%3E7Vnbkto4EP0aHpOyMYbhkdsmqZrJTiCX3X3TWA2oRrZYWdzy9WnJki%2FYzJBkgK3aqaJc9FFLanUfNd2mFYzi3TtJVss7QYG32h7dtYJxq93u9Tv41MA%2BAzp%2BOwMWktEM8gtgxr6DBT2LrhmFtKKohOCKrapgJJIEIlXBiJRiW1WbC17ddUUWUANmEeF19BujapmhN6FX4O%2BBLZZuZ9%2BzIzFxyhZIl4SKbQkKJq1gJIVQ2bd4NwKufef8ks3748hobpiERJ0yYbqicjf59E%2F4brfqfiR%2FDt730jd2lQ3ha3vgLynI1Fqs9s4N6ZbFnCQoDeciUTM74qEcLRmnt2Qv1tqMVJHo0UnDpZDsO%2BoTjkM%2BAjgslY1y4FU0ZnqmXVNCijr37mz%2BAXRHdhXFW5IqZ43gnKxS9mDs0xNjIhcsGQqlRGyVtkumYLYikdbZIm%2B1ISrOjXSR0oJ1EEgFu6Oe9%2FN44j0AEYOSe1RxE3r9bMrecdvegW3BqLbjzbLEpqBjQWJZvMjXLgKNX2ysfyLu7VrcP%2BADfTPA50xJlixqHEAHKBNCKR5hJLiQiCciIwXj%2FAAinC0SFDnM9TTtQYb3amDhmFGqVx6mGAfc7taojTsFMrWO0JDA6XNugrLEiYArDFeCJco4JhziB1018t6GrRBtHaHsFzJ%2BtLpUI5Gg%2BYSZuAGyZguaOUMpFFHkIef0MwQ5iRPH71udKJYYgXciL85Fi6BGi48khldiXJsY3WsTo1MjxiQmjNeY0eXaa5Rt8OtCGY9k0IM8RNCOBr0SdE%2FSdCskPbLJs%2FN%2FY%2BsP%2BAvoDSLFNgX5h1h2AEkutv1XvCeUKKDXsAAvKUmi4vBjsdaX4AI7T4Xexyv9HN2RZG%2FKPnzoDNL2psCJYuIinhhJsHt5Y4xG4REUPrP4Ij4x9Q06QlA2Z4YQlzLlNdNfONP3T8z07fBcqT6spXp9Jf%2FnLYFhh84CmbY7%2By1L3JqVpiEWG1JsZywqyyWqaRlbZijLQFlZ5CJ6BJofxvS5peEXaFA6rr5wDcpNvUHx%2B00s9M7Fwu5rg%2FIb2emArr%2BerMKf7VcaaXK2srR3Wr%2Fy8qUhjVlytcpwtl6BvLoVo3WK6dMYot8ZXcOO%2F0JtdpGpr4nuuomuof9uTHT%2B2V7Y3dQyXY0VQBfgqjA8LFN71ydNipGsVsuqLb9ddSYkdKBfmaM4mSJ1PgvTemkvrxOalyC4ttz%2FpYW3%2FX7ggL8NEIQ9B4x3Zf3xvizdg8TrqUBW4qYP8HQFg%2BcVaxnBc4HEbhFLPHgq4J3mgJcj3FR356A0vt1UDW4Ku93jXt%2BRUsnVqZZcOcXcEtlB7azya%2F3DhcLqQocMzBxRW8eQMD%2F3r%2FOy38DLLIdpolUI2v13LdzAm9RQUCdrDPMuS3123KU9%2B6syA7lhUTmXZysfyY1Vrr5EF%2Fd07Zz%2Fv%2BMCWU8VnSYiHcb7hEyBYvF3URbA4j%2B3YPID%3C%2Fdiagram%3E%3C%2Fmxfile%3E)

* Transaction Service - [Transaction Service ER Diagram](https://viewer.diagrams.net/?tags=%7B%7D&lightbox=1&highlight=0000ff&edit=_blank&layers=1&nav=1&title=Transaction%20Service.drawio#R%3Cmxfile%3E%3Cdiagram%20name%3D%22Page-1%22%20id%3D%224suCxPCX7-hQDB8hAZGk%22%3E7Vxbc5s4FP41fkwHsCH2o2%2FdzUwuntiZNvvSUUC2tQVEhYjt%2Fvo9AgEG4dSZGuxumMkk1kGXw%2Fk%2BHT5JJp3u2Nv%2BxVCwvqMOdjuG5mw73UnHMK4HPfgtDLvE0NONxLBixElMem6Yk59YGjVpjYiDw0JFTqnLSVA02tT3sc0LNsQY3RSrLalbHDVAK6wY5jZyVesX4vB1Yu2bWm7%2FG5PVOh1Z1%2BQVD6WVpSFcI4du9kzdaac7ZpTy5JO3HWNXxC6NS9Lu84GrmWMM%2B%2FyYBtaPL94Ez75d%2FePd6eHK%2BXfw07iSvbwiN5I33DEsF%2FobLSl0C17znQyF9SOi6YWrMAZqCBXABcB6lF%2BHTyvxd8GQHyKbE%2BpDtTlmr8TGae%2FgZjJAUldGKBvLYDTyHSw81%2BDyZk04ngfIFlc3wDOwrbnnQknPWr9ixvH2YHD0LORAVUw9zNkOqsgGGUqSpmlxk2PeS5Fd7%2BFtSRuSNFtlPedIwAcJxjuAMRRgFjSIgPTlSIUb4rnIxxKZubwiwmavievcoh2NhLchR%2Fb3tDRaU0Z%2BQn2UBhEuMy7nX1cr1JiLlrJPhgX0szSwesl0h7aFirco5Kk31HVREJKX2D%2FR0ENsRfwR5Zx6xyGdzaETwT4ooq7rKuxGFezdXl24dxXcb%2BCXlsy1OWfEXykUgPvnMYKMfsdj6lIGdp8mnCCuWzIhl6x8KLp4KZqJABJIeENp9ojjiJ5HIcAAw93G1Sa93PIoAyFMFJov3RiTNTTE0MMooMTncWDMEfxAqMbaJ7Njgq9jKOt5GX5EdcbH1Af3EYlhw0CaDRbEGTHKEUcvGaVPkQkOzzeVJ5IY3WN5URctegothh6kSJ5RY0IjEaWWGk1Twzo3NUyFGlMPEVdJGsmD1yGv%2BUM3Mb2wsgX8qKi3Z3oKMTTSKlLTkR38xtg38AzUptuAMNAH6fAj0IQY%2BU2M%2F4ghvH58s9qBSVi%2FE0VxNUEc505AYUG8RtyIYdid04NYYBgarDvIksSEOJsrIlFFXpAKXshOUZg5MvUjrwYn2nTfcLofHJnuDbOufG8p%2BX6CnShOBe3q4KSrg0zlp8sD0zw3%2BNft8uAsScG67OVBv10eXCg1zr48GLTLg0aXBxeizOPdwhiDsH4Q2qzScFY5VoXqte1Ppzul%2BxvUOfNbIXpSIWr2S0K0d%2BwqpF8b%2FurJUatEm0gMulZNlUuRoun5bqtFL48cZxejunq89QHU6DyybRyGyyi%2FzwYF6T3eiAERPH1trEzCjyOIC24sdkHuRrs3%2Bz%2FJb0fL4kFtCU49qD1wLJCQ7iOr5BMI42uzoItNw1IJ0K0igFEbAdTj2PuHxbfxw%2F386W46abNCHVmh925JXEWK%2BlSPemQzGz4uboa3t88tM87IjCo93Cwz1POcz08tK87LiioV0Swr1OOcSvXaSogTSAjTKmmIqu%2BANqwh1DObxcPsadYmgzqSQf%2ByxUPKsv3ve0wnT%2BNFy4bm2XB2wWCo2%2B7Tr7Obx%2BeWDc2z4exCwVD32hUeYN8Zivegcqj3QlN8zQZvCf8qPgMOSel578pku1%2FYyUIyHnaUt6hKz1%2FwiUbMxm%2Fdjdw7AeGxwm%2FBYlajsgeDWYFCamPYRZy8Fv2tgkaOMBPEzfVCdqSa6gWthG5yn7JVDrDa0UAvdNQzjWJHSRyUjmKmZLf9G%2BRR9%2BKbJg9QhO32Goli3OrTYNBNDXnTuFRoO8OMQBwwq4GN1pFsTM40zkVHyyzK1155a%2FNYOl5rpY7KrzjVTUd14%2BwPzmX6sbnMOLAWbSiZlV6AyzZQ35%2FMih31yt%2BArZs96r67wp5wjQLxkbKp65IgFPwJ9hIITqx7OeVXyuIF1sGrmHcPEXeJny5kj1Icby9L9f6BgBbONlRi1LYoNdStqvZxoe7Z%2FHrCH%2FhmV0OPixKtst2O9074cke9dNOiqQmv7qn%2F2RPeui4FtOIV9BNNeCjm%2F3YgAST%2F3w3d6X8%3D%3C%2Fdiagram%3E%3C%2Fmxfile%3E)

* Email Service - [Email Service ER Diagram](https://viewer.diagrams.net/?tags=%7B%7D&lightbox=1&highlight=0000ff&edit=_blank&layers=1&nav=1&title=Email%20Service.drawio#R%3Cmxfile%3E%3Cdiagram%20name%3D%22Page-1%22%20id%3D%22-dOVXPWLHoVtLr2v8MKx%22%3E7VfbbptAEP0aHlNxsd34MRC3jZSoUUhV9alawxhWWRi6rI2dr%2B8sLGBCrDpq4rxEQpg9c%2FWcwwKWF2Tbr5IV6Q3GICzXjreWd2m57uf5hM4a2DXAxHEbIJE8biCnB0L%2BCAa0DbrmMZQDR4UoFC%2BGYIR5DpEaYExKrIZuKxTDqgVLYASEERNj9CePVdqg51O7x78BT9K2smMbS8ZaZwOUKYux2oO8heUFElE1V9k2AKFn186liftywNo1JiFXxwRUm5s7e%2BOF5%2FBLuHwXP%2F6uzs5Mlg0Ta%2FOHLXcmKJ%2B%2FQkpLXaudGcXszxpbw1lZE3VBDtQCce33drpK9O8iY1zXD0FueARtXmqwSd14mdl0VVyJ6zwG3bNN5irlCsKCRdpakcIIS1UmaOV00RuQCrYHx%2BJ0wyaRAmag5I5cTEDHjxFou6x6tictp%2Bke0zODMSOwpMvcc0AXhoYXUOKOKPl%2BfzsaU1nxTLAcDCGhseiZRSkX8TXb4Vq3WioWPbQrP0XJH8mftRMks1TmtvPsgUeoI01OCZrx23aqzhPohm0HjtesVG03KAQrSr6s%2B9OBGZMJz31UCrPjaO5unVfi3HPmA84dZ0y6%2Bxzp7vlbse6NWL%2Bik93cY6GSPE9GGqABqJpCiQ8QoEBJeI6NKLgQTyAmeJLTUsBKh%2BkJctroLgyc8TjWmf2SeKBy17Xb5aRH7swgNIQUvhI1KSkFAmXwC%2BS5qgcz9emgUQX2p6k1pV4DWjv9mg7tLlWAObXPeM0bkGoq0MrxJSqm2LLT9GvsA4fvtrFQjDC8I3XhvZUsJs9vBh%2B6eF9dzN5bF9ORLtrH7VAZzTM35pv%2BedtAS%2FkUoT6e8duDbllZVijjA0X%2BGf8fpa9KKvejhL62Ty%2BBwPJTFF9sCy6VZv%2BSKeg60It7nsEpWggkMMUxf88e6me6a9MbPl%2FxmolTtfKxvZ14e5sfub0585fvb7Tsv3lq296Ho7f4Cw%3D%3D%3C%2Fdiagram%3E%3C%2Fmxfile%3E#%7B%22pageId%22%3A%22-dOVXPWLHoVtLr2v8MKx%22%7D)

* API Gateway Service [API Gateway Service ER Diagram](https://viewer.diagrams.net/?tags=%7B%7D&lightbox=1&highlight=0000ff&edit=_blank&layers=1&nav=1&title=API%20Gateway%20Service.drawio#R%3Cmxfile%3E%3Cdiagram%20name%3D%22Page-1%22%20id%3D%22Q9tkc2uN9IqcutxmOZqX%22%3E7VjbcpswEP0aP6YD%2BJL40RDnMuNk0tJOp48KrEGNQFQSxs7XdwUCjImnziSx%2B5AZj4PO7mpP9qxg8WDoJetrQbL4jofABo4VrgfDy4HjnE9H%2BK2BTQWMbKcCIkHDCrJbwKfPYEDLoDkNQXYcFedM0awLBjxNIVAdjAjBi67bkrNu1oxE0AP8gLA%2B%2BpOGKq7Qi7HV4jdAo7jObFvGkpDa2QAyJiEvtqDhfDD0BOequkrWHjBdu7ouVdzVHmtDTECqDgkg63jpXv%2F%2BeuUlwa8QIhXDzdm42mVFWG7%2B4YEzYbifu%2BS4LbJWG1OKyZ%2Bc14YzWQo1QwekgFq7rR2vIv139nCL5muioCAbvPJBrGgA9f5ItEpReZsaNdkcwfM0BM3dQnMRUwV%2BRgJtLbDTEItVwnBlN9ErEArWe8tjN0XHZgWegBKalwlwauHqRjXLolV9VLvEW4pPDEZMo0XNzq0WeGHkeIU0k540PyQI2SuULGjCSApGGt9YdNWCmLJwQTY812SlIsFTvXJjLugz%2BpO6hmgWyhzAodXx8HWk2VOA1v6hrqu9A92RdcdxQaSq2XDGSCbpY8lPByZERDR1uVI8OUzo5hC9l%2BrnXdXtYV%2F2RuJt2UcXH6X7eU%2F3W%2FyyqtPmK0HTqNcDWABVSij4E3iccYF4yqumoIztQITRKMUlg6UO0xWkeMubGTihYah3diXqgOkWpdvlqEW%2BmUJoiGP4kpWixBgIuIObcZqqsjBjFz9YKs%2F6Mh6MkauHa7td40e7C%2BXxFOkTWuoG2DUF6M5xBVdEkcemp9%2FjTrD%2FvPUbxTTGoX0x%2FKi2uOi1xT1J4LMxTt0Yk1M3xrTXGPOEUNbrjOqxG9JV%2B8itoEexiyCPF%2Fy2oAciZcFFuCfJP%2BPfkFo%2FBNOXOv%2FjU9%2Fiw9eahQlNm%2BQuzqJA0qNl9%2FMMxMlZeLnEJ3ZJRMtxCh7zVB%2F98BSpZ7nSo5GiIJv0Cyr1KEsSfdcpA%2FB1KFUQ1s6bxvq6ZG%2FhGQQ4Q%2Bsh%2Fp7rXpmvMypOVLIdKgsePJ2GiYcVwNs9JUz%2BD4VBOkTRksUlvik1FPTiO03gGBzKEd2x8NWdLmlZhmNR%2BRxWjjysTA8dVqavn1Zw2f6YUdq2fhEazv8C%3C%2Fdiagram%3E%3C%2Fmxfile%3E)


## Usage Instructions

    1. Download the given postman collection - Loyalty_Reward_System.postman_collection.json and import it in the postman.

    2. Clone this repository.

    3. Pre-requisites to run this project: Java 17, Eclipse IDE, Postman, MySQL workbench, ElasticSearch, LogStash, Kibana, Apache Kafka, Filebeats, Ubuntu, Redis server

    4. Windows subsystem for Linux should be enabled.

    5. MySQL server, ElasticSearch server, LogStash server, Kibana server, Apache Kafka server, Zookeeper server, Ubuntu WSL2, Redis server should be up and running.

    6. Import this project to Eclipse IDE & update the project in order to download all maven dependencies mentioned in pom.xml of all the microservices.

    7. In Eclipse, go to help->Install new software-> Put the following URL - https://projectlombok.org/p2 and download lombok. This is required to auto-generate getters and setters.

    8. Configure the MySQL database and create a databases as mentioned in application.properties of all the microservices.

    9. If pom.xml gives an error on the external resources link, you will have to download them in order to mitigate it.

    10. Start Service Registry server first, then start all other servers.

    11. Open service registry's dashboard & make sure all the services are running fine.

    12. Run the APIs in the postman.


## Functionality

* Signup - Customer user : 

        1. User hits SignUp API, which takes user's data input and creates a user entry in API gateway service. 
        2. While entering the password, it encrypts the password using BCryptEncoder. 
        3. Then it makes a call to User service where User's entry will be created with balance as 0. 
        4. Then, it makes a call to Email service which generates an OTP entry & embeds the OTP in the email & triggers the email. 
        5. A response will be sent to the user.

* Signup - Admin user :

        1. User hits GenerateAdminSecret API by putting the master password as the input request parameter. If the password matches the password saved in the properties in base64 encoded form, a secret will be generated & an entry will be created against the same.
        2. User hits SignUp API by including the admin secret, which takes user's data input and creates a user entry in API gateway service.
        3. It makes a call to AdminService to validate the admin secret and that secret will be marked as used. 
        4. While entering the password, it encrypts the password using BCryptEncoder. 
        5. Then it makes a call to User service where User's entry will be created with balance as 0. 
        6. Then, it makes a call to Email service which generates an OTP entry & embeds the OTP in the email & triggers the email. 
        7. A response will be sent to the user.

* Verify OTP :

        1. User puts the email ID and the received OTP.
        2. A call will be made to Email Service which validates this OTP by checking the entry created against this OTP.
        3. It checks if email ID is correct, it is not yet expired & this OTP is not consumed already.
        4. If it is correct, then the user's is Validated flag will be marked as true.
        5. Signup session details will be pushed to admin service via Apache Kafka broker.

* Resend OTP :

        1. It takes email ID as the input.
        2. It makes a call to Email service which makes the existing OTP expire and generates a new OTP.
        3. A new email will be sent with this new OTP.

* Login :

        1. User puts the email ID and password.
        2. Authentication mechanism checks if an entry exists in the database for this email ID and fetches the entry.
        3. User given password will be encoded by BCryptEncoder and matched against the encoded password saved in the database.
        4. If it matches, a JWT token will be generated with a fixed expiry time.
        5. Login session details will be pushed to admin service via Apache Kafka.
        6. Same token will be returned as response. This token needs to be entered as Bearer Token in every other API from now on in order to authenticate itself.
        7. JWT authentication is applicable for all the endpoints belonging to all the microservices except Signup, VerifyOTP, ResendOTP, Login & GenerateAdminSecret.
        8. Everytime the user is authenticated, Apache kafka sends a message to admin service with updated time details. The same will be updated for the present active session entry.

* Topup :

        1. It takes email ID and amount as the input. 
        2. After the authentication from API gateway is successful, it is routed to Transaction Service by service registry.
        3. It adds this topup amount to the user's balance, also creates an entry in Topup table by calculating the time when it should get expired based on the property set in the application.properties. Topup table will have amount and remaining amount as two different fields. This is because, when a deduction happens, it should pick the required amount from the oldest topup entry.
        4. It also creates an entry in Transaction table.
        5. It makes a call to user service to update the balance accordingly.

* Deduction :

        1. It takes email ID and amount as the input. 
        2. After the authentication from API gateway is successful, it is routed to Transaction Service by service registry.
        3. It makes a call to user service to fetch present balance & validates if the amount selected to be debited is less than the total balance.
        4. If yes, it checks & picks the topup entries one by one, in the order of oldest to newest. It deducts the respective amount in 'remaining balance' column & if remaining balance becomes 0, consumption status will be made as FULLY_CONSUMED. If remaining balance is less than amount, but not equal to 0, then status will be made as PARTIALLY_CONSUMED.
        5. It also creates an entry in transaction table.
        6. It makes a call to user service to deduct the amount accordingly.

* Passbook :

        1. It takes email ID as input.
        2. After the authentication from API gateway is successful, it is routed to Transaction Service by service registry.
        3. It fetches all entries associated with the given email ID in transaction table and returns the same.

* Get all login sessions by ID, Get all signup sessions by ID :

        1. It checks if the token entered belongs to an admin or super admin. 
        2. Then makes a call to admin service to fetch the entries accordingly.

* Scheduled jobs :

        1. killInactiveSessions : It runs once in every hour in admin service to fetch all the active login sessions whose last modified date is 1 hour ago. It makes all those sessions as inactive.
        2. killExpiredOTPs : It runs once in every 15 minutes in email service to fetch all the OTP entries whose expiry time is a past time and yet not marked as expired. It marks them as expired.
        3. killExpiredTopups : It runs once in every hour in transaction service to fetch all the topup entries whose expiry date time is a past date time, consumption status is not FULLY_CONSUMED and yet isExpired attribute is not marked as true. It makes the status of those entries as expired, creates entries in transaction table with amount = remaining balance with type = EXPIRY. It also deducts that much of amount from User's entry in user service by making a call to user service.


* JWT Authentication :

        1. It is implemented using Spring Security and Spring Webflux in reactive way. Except signup, login & few initial APIs, all other APIs need to be authenticated. 
        2. Once authentication is successful, JWT token will be generated which will have a signature, expiry time, username and claims.

* Redis Cache :

        1. It is implemented as a distributed caching system.
        2. @Cacheable is used for all GET operations.
        3. @CachePut is used for all DML operations.
        4. @CacheEvict is used for all delete operations.
        5. A unique key will be used to store it.
        6. Redis CLI can be used to test the stored data.




## Developer

* Nishal Beegamudre

</br></br><a  href="https://www.linkedin.com/in/nishal-beegamudre/" target="_blank"><img alt="LinkedIn" src="https://img.shields.io/badge/linkedin%20-%230077B5.svg?&style=for-the-badge&logo=linkedin&logoColor=white" /></a>
