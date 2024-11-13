# EcommerceShop

EcommerceShop is a modular e-commerce platform that provides both admin and customer. This project is designed using a modular architecture for easy maintenance and flexibility. The root project, `EcommerceShop`, consists of multiple submodules, each handling different components of the e-commerce system.

## Project Structure

EcommerceShop is structured with three main submodules:

### 1. MyShopCommon
- **Purpose**: Contains shared entities and custom exceptions used across the entire project.
- **Features**: 
  - Provides a central library of data models and error-handling classes, ensuring consistency across all modules.
  - Used as a dependency by both the `MyShopBackend` and `MyShopFrontend`.

### 2. MyShopChat
- **Purpose**: Dedicated to chat services
- **Features**: 
  - APIs for adding and retrieving messages, data stored in mongodb
  - Used as a dependency by both `MyShopBackend` and `MyShopFrontend` to add and retrieve data from mongodb.

### 3. MyShopWebParent
- **Purpose**: Parent module containing the main backend and frontend applications.
- **Submodules**:
  - **MyShopBackend**: A Spring Boot application for administrative functions.
    - **Features**:
      - Manages products, categories, brands, users, customers, orders, shipping, sale reports, settings, and customer chat.
      - Uses `MyShopCommon` for shared data models and `MyShopChat` for message's data operation
    - **Dependencies**: `MyShopCommon`, `MyShopChat`
    - **Link deploy**: https://my-us-shop-admin.onrender.com/MyshopAdmin
    - Can login with username: leminhhoang123456le@gmail.com , password: 123456
    
  - **MyShopFrontend**: A Spring Boot application serving as the customer-facing website.
    - **Features**:
      - Allows customers to browse products by category, view product details, place orders, update delivery address, make payments, chat with admins, and interact with a chatbot for product recommendations.
      - Uses `MyShopCommon` for entities and `MyShopChat` for chat functionalities.
    - **Dependencies**: `MyShopCommon`, `MyShopChat`
    - **Link deploy**: https://my-us-shop.onrender.com/Myshop
    
## Prerequisites
- Java 11+
- Maven
- Docker (optional, for containerized deployment)
  
## Environment Variables
To keep sensitive information secure, use environment variables to store values:
- `US_SHOP_DB_URL` : jdbc:mysql://bxblhmrq6sz1wvkb5ukh-mysql.services.clever-cloud.com:3306/bxblhmrq6sz1wvkb5ukh
- `US_SHOP_DB_USER` : u37l9dqp82mxrfuo
- `US_SHOP_DB_PASSWORD` : 9MGDeTtdxcoc5vzqHrXR
- `MONGO_DB_URI` : mongodb+srv://minhhoang:tryMiyNjxDol9fmj@myusshop.mee5s.mongodb.net/myshopchat?retryWrites=true&w=majority&appName=MyUSShop
- `AWS_ACCESS_KEY_ID` : AKIA4SDNVTGZUVVVLL72
- `AWS_BUCKET_NAME` : myusshop
- `AWS_SECRET_ACCESS_KEY` : 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
- `AWS_REGION` : ap-southeast-1
- `ADMIN_DOMAIN_URI` : localhost:8080
- `CLIENT_DOMAIN_URI' : localhost:8081
- `AI_SERVER_URI` : https://similarity-search-products-recomendation.onrender.com
- `CAPTCHA_SECRET_KEY` : 6LcMS1EqAAAAAOs6qFBZ6D79eOvpAKZT2agylFgU
- `OAUTH_FACEBOOK_CLIENT_ID` : 795009705854715
- `OAUTH_FACEBOOK_CLIENT_SECRET` : f09097ff412d53978ed4ae47b5984904
- `OAUTH_GOOGLE_CLIENT_ID` : 326217566106-m45dv35olkf9a9ea0bffochfb9evqmnd.apps.googleusercontent.com
- `OAUTH_GOOGLE_CLIENT_SECRET` : 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
- `REDIS_HOST` : redis-18986.c309.us-east-2-1.ec2.redns.redis-cloud.com
- `REDIS_PASSWORD` : EWmsVomApjH4X4MN1NCQQ3c09Zb1MzbB
- `REDIS_PORT` : 18986

## Building the Project
1. Clone the repository:
   ```bash
   git clone https://github.com/mihoag/E-commerce-Website.git
2. Run the Spring Boot application:
   ```bash
   mvn clean install
## Running the Applications
Each submodule can be run independently
### MyShopBackend
1. Navigate to the backend directory:
   ```bash
    cd MyShopWebParent/MyShopBackend
2. Run the Spring Boot application:
   ```bash
    mvn spring-boot:run
### MyShopBackend
1. Navigate to the frontend directory:
   ```bash
    cd MyShopWebParent/MyShopFrontend
2. Run the Spring Boot application:
   ```bash
    mvn spring-boot:run
## License
This project is licensed under the MIT License.
