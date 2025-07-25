import React from 'react';
import { Link } from 'react-router-dom';
import { TrendingUp, Users, MapPin, Clock } from 'lucide-react';

const Home = () => {
  const features = [
    {
      icon: <TrendingUp className="h-8 w-8 text-green-600" />,
      title: "Real-time Bidding",
      description: "Participate in live auctions with instant bid updates and transparent pricing."
    },
    {
      icon: <Users className="h-8 w-8 text-blue-600" />,
      title: "Multi-user Platform",
      description: "Connect farmers, traders, and commission agents in one unified marketplace."
    },
    {
      icon: <MapPin className="h-8 w-8 text-purple-600" />,
      title: "Pan-India Network",
      description: "Access mandis across India with inter-state trading capabilities."
    },
    {
      icon: <Clock className="h-8 w-8 text-orange-600" />,
      title: "Live Auctions",
      description: "Countdown timers and real-time auction management for efficient trading."
    }
  ];

  const stats = [
    { label: "Active Mandis", value: "1,473+" },
    { label: "Commodities", value: "209" },
    { label: "Registered Users", value: "50L+" },
    { label: "States Connected", value: "18" }
  ];

  return (
    <div className="min-h-screen">
      {/* Hero Section */}
      <section className="bg-gradient-to-r from-green-600 to-blue-600 text-white py-20">
        <div className="container mx-auto px-4 text-center">
          <h1 className="text-5xl font-bold mb-6">
            National Agriculture Market
          </h1>
          <p className="text-xl mb-8 max-w-3xl mx-auto">
            A comprehensive simulation of India's e-NAM portal featuring real-time bidding, 
            transparent pricing, and seamless agricultural commodity trading across the nation.
          </p>
          <div className="space-x-4">
            <Link 
              to="/auctions" 
              className="bg-white text-green-600 px-8 py-3 rounded-lg font-semibold hover:bg-gray-100 transition duration-300"
            >
              View Live Auctions
            </Link>
            <Link 
              to="/register" 
              className="border-2 border-white text-white px-8 py-3 rounded-lg font-semibold hover:bg-white hover:text-green-600 transition duration-300"
            >
              Join e-NAM
            </Link>
          </div>
        </div>
      </section>

      {/* Stats Section */}
      <section className="py-16 bg-white">
        <div className="container mx-auto px-4">
          <div className="grid grid-cols-2 md:grid-cols-4 gap-8 text-center">
            {stats.map((stat, index) => (
              <div key={index} className="p-6">
                <div className="text-3xl font-bold text-green-600 mb-2">{stat.value}</div>
                <div className="text-gray-600">{stat.label}</div>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section className="py-16 bg-gray-50">
        <div className="container mx-auto px-4">
          <h2 className="text-3xl font-bold text-center mb-12">Platform Features</h2>
          <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-8">
            {features.map((feature, index) => (
              <div key={index} className="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition duration-300">
                <div className="mb-4">{feature.icon}</div>
                <h3 className="text-xl font-semibold mb-3">{feature.title}</h3>
                <p className="text-gray-600">{feature.description}</p>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* How it Works */}
      <section className="py-16 bg-white">
        <div className="container mx-auto px-4">
          <h2 className="text-3xl font-bold text-center mb-12">How e-NAM Works</h2>
          <div className="grid md:grid-cols-3 gap-8">
            <div className="text-center">
              <div className="bg-green-100 rounded-full w-16 h-16 flex items-center justify-center mx-auto mb-4">
                <span className="text-green-600 font-bold text-xl">1</span>
              </div>
              <h3 className="text-xl font-semibold mb-3">Register & Verify</h3>
              <p className="text-gray-600">
                Create your account as a farmer, trader, or commission agent and complete verification.
              </p>
            </div>
            <div className="text-center">
              <div className="bg-blue-100 rounded-full w-16 h-16 flex items-center justify-center mx-auto mb-4">
                <span className="text-blue-600 font-bold text-xl">2</span>
              </div>
              <h3 className="text-xl font-semibold mb-3">List or Browse</h3>
              <p className="text-gray-600">
                Farmers list their produce while traders browse available commodities across mandis.
              </p>
            </div>
            <div className="text-center">
              <div className="bg-purple-100 rounded-full w-16 h-16 flex items-center justify-center mx-auto mb-4">
                <span className="text-purple-600 font-bold text-xl">3</span>
              </div>
              <h3 className="text-xl font-semibold mb-3">Trade & Transact</h3>
              <p className="text-gray-600">
                Participate in real-time auctions, place bids, and complete secure transactions.
              </p>
            </div>
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="py-16 bg-gradient-to-r from-blue-600 to-green-600 text-white">
        <div className="container mx-auto px-4 text-center">
          <h2 className="text-3xl font-bold mb-6">Ready to Start Trading?</h2>
          <p className="text-xl mb-8 max-w-2xl mx-auto">
            Join thousands of farmers and traders who trust e-NAM for transparent, 
            efficient agricultural commodity trading.
          </p>
          <Link 
            to="/register" 
            className="bg-white text-blue-600 px-8 py-4 rounded-lg font-semibold text-lg hover:bg-gray-100 transition duration-300"
          >
            Get Started Today
          </Link>
        </div>
      </section>
    </div>
  );
};

export default Home;