# e-NAM Simulation Platform

A comprehensive simulation of the **National Agriculture Market (e-NAM)** portal featuring real-time bidding functionality. This project demonstrates a modern agricultural marketplace built with **Spring Boot backend** and **React frontend**.

## 🌟 Features

### Core Functionality
- **Real-time Bidding System** - Live auction environment with WebSocket support
- **Multi-user Support** - Farmers, Traders, Commission Agents, and Administrators
- **Commodity Management** - Support for 200+ agricultural commodities
- **Mandi Integration** - Virtual APMC market yards across India
- **Transparent Pricing** - Real-time price discovery mechanism
- **Quality Grading** - A, B, C grade classification system

### Real-time Features
- **Live Auction Dashboard** - Real-time bid updates
- **WebSocket Integration** - Instant bid notifications
- **Auction Timer** - Countdown timers for active auctions
- **Dynamic Price Updates** - Live price movements
- **Bid History** - Real-time bid tracking

### User Management
- **Role-based Access Control** - Different permissions for different user types
- **JWT Authentication** - Secure token-based authentication
- **User Registration** - Multi-step registration process
- **Profile Management** - User profile and verification

### Market Features
- **Multi-state Trading** - Inter-state commodity trading
- **Quality Parameters** - Commodity quality specifications
- **MSP Integration** - Minimum Support Price display
- **Market Analytics** - Trading statistics and reports

## 🏗️ Architecture

### Backend (Spring Boot)
- **Spring Boot 3.2.0** - Main framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Database operations
- **WebSocket (STOMP)** - Real-time communication
- **H2 Database** - Development database
- **JWT** - Token-based authentication
- **Maven** - Dependency management

### Frontend (React)
- **React 18** - UI framework
- **React Router** - Navigation
- **STOMP Client** - WebSocket communication
- **Axios** - HTTP client
- **Chart.js** - Data visualization
- **Tailwind CSS** - Styling
- **React Hook Form** - Form management

## 🚀 Quick Start

### Prerequisites
- **Java 17+**
- **Node.js 16+**
- **npm or yarn**
- **Git**

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd enam-simulation
   ```

2. **Install frontend dependencies**
   ```bash
   npm run install-frontend
   ```

3. **Start the development servers**
   ```bash
   npm run dev
   ```

   This will start:
   - Backend server at `http://localhost:8080`
   - Frontend application at `http://localhost:3000`

### Alternative Setup

#### Backend Only
```bash
cd backend
./mvnw spring-boot:run
```

#### Frontend Only
```bash
cd frontend
npm install
npm start
```

## 📡 API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration
- `GET /api/auth/me` - Get current user

### Bidding System
- `POST /api/bidding/place` - Place a bid
- `GET /api/bidding/listing/{id}` - Get bids for listing
- `GET /api/bidding/active-auctions` - Get active auctions
- `POST /api/bidding/start-auction/{id}` - Start auction
- `POST /api/bidding/end-auction/{id}` - End auction

### Real-time WebSocket Endpoints
- `/ws` - WebSocket connection endpoint
- `/topic/auction/{listingId}` - Auction-specific updates
- `/topic/auction-status` - General auction status updates

## 🔌 WebSocket Integration

### Frontend Connection
```javascript
import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

const socket = new SockJS('http://localhost:8080/api/ws');
const stompClient = Stomp.over(socket);

stompClient.connect({}, (frame) => {
  // Subscribe to auction updates
  stompClient.subscribe('/topic/auction/1', (message) => {
    const bidUpdate = JSON.parse(message.body);
    // Handle real-time bid updates
  });
});
```

### Backend Broadcasting
```java
@Autowired
private SimpMessagingTemplate messagingTemplate;

// Broadcast bid update
messagingTemplate.convertAndSend(
    "/topic/auction/" + listingId, 
    bidResponse
);
```

## 🎯 Real-time Bidding Flow

1. **Auction Start** - Admin/Farmer starts auction
2. **Real-time Updates** - All connected users receive live updates
3. **Bid Placement** - Traders place bids through REST API or WebSocket
4. **Bid Validation** - Server validates bid amount and user eligibility
5. **Broadcast Update** - New bid broadcasted to all subscribers
6. **Auction End** - Automatic or manual auction closure
7. **Winner Declaration** - Highest bidder wins

## 🗄️ Database Schema

### Key Entities
- **User** - Farmers, Traders, Admins
- **Commodity** - Agricultural products with MSP
- **CommodityListing** - Items available for auction
- **Bid** - Individual bid records
- **Transaction** - Completed sales
- **Mandi** - Market yards/APMCs

### Relationships
```
User (1) -> (*) CommodityListing (Farmer)
User (1) -> (*) Bid (Bidder)
CommodityListing (1) -> (*) Bid
Commodity (1) -> (*) CommodityListing
Mandi (1) -> (*) CommodityListing
```

## 🔧 Configuration

### Backend Configuration (`application.yml`)
```yaml
server:
  port: 8080
  servlet:
    context-path: /api

spring:
  datasource:
    url: jdbc:h2:mem:enam_db
  
enam:
  jwt:
    secret: your-secret-key
    expiration: 86400000
```

### Frontend Proxy (`package.json`)
```json
{
  "proxy": "http://localhost:8080"
}
```

## 🧪 Testing

### Backend Tests
```bash
cd backend
./mvnw test
```

### Frontend Tests
```bash
cd frontend
npm test
```

### Integration Testing
1. Start both servers
2. Navigate to `http://localhost:3000`
3. Register users with different roles
4. Create commodity listings
5. Start auctions and place bids
6. Observe real-time updates

## 📊 Key Components

### Backend Components
- **BiddingController** - REST and WebSocket endpoints
- **BiddingService** - Business logic for auctions
- **WebSocketConfig** - Real-time communication setup
- **SecurityConfig** - Authentication configuration

### Frontend Components
- **AuctionDashboard** - Real-time auction interface
- **BiddingPanel** - Bid placement interface
- **AuctionTimer** - Countdown timer component
- **LivePriceChart** - Real-time price visualization

## 🔒 Security Features

- **JWT Authentication** - Secure token-based auth
- **Role-based Access** - Different permissions per user type
- **CORS Configuration** - Cross-origin request handling
- **Rate Limiting** - API abuse prevention
- **Input Validation** - Server-side validation
- **SQL Injection Prevention** - JPA query protection

## 📈 Performance Optimizations

- **Database Indexing** - Optimized queries
- **Connection Pooling** - Efficient database connections
- **Lazy Loading** - JPA lazy fetching
- **WebSocket Efficiency** - Targeted message broadcasting
- **Frontend Caching** - Component state management

## 🌐 Deployment

### Backend Deployment
```bash
cd backend
./mvnw clean package
java -jar target/enam-simulation-1.0.0.jar
```

### Frontend Build
```bash
cd frontend
npm run build
```

### Docker Support (Future Enhancement)
```dockerfile
# Dockerfile for backend
FROM openjdk:17-jre-slim
COPY target/enam-simulation-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 🤝 Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open Pull Request

## 📝 API Documentation

Once the application is running, visit:
- **Swagger UI**: `http://localhost:8080/api/swagger-ui.html`
- **API Docs**: `http://localhost:8080/api/api-docs`

## 🎥 Demo Scenarios

### Scenario 1: Farmer Lists Produce
1. Login as farmer
2. Create commodity listing
3. Set base price and quality grade
4. Submit for approval

### Scenario 2: Real-time Auction
1. Admin approves listing
2. Auction starts automatically
3. Multiple traders place bids
4. Real-time price updates
5. Auction ends with highest bidder

### Scenario 3: Inter-state Trading
1. Farmer from State A lists produce
2. Trader from State B participates
3. Successful transaction across states
4. Payment and delivery coordination

## 🏆 Project Highlights

- **Real-time bidding** with WebSocket technology
- **Scalable architecture** with Spring Boot and React
- **Comprehensive user management** system
- **Market simulation** with actual e-NAM features
- **Professional-grade** code quality and documentation
- **Modern UI/UX** with responsive design
- **Security-first** approach with JWT and validation

## 📞 Support

For questions or issues:
- Check the documentation
- Review the code comments
- Open an issue on GitHub
- Contact the development team

---

**Note**: This is a simulation project for educational and demonstration purposes. It replicates the core functionality of the actual e-NAM portal but is not affiliated with the Government of India's official e-NAM platform.