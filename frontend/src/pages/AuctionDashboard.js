import React, { useState, useEffect } from 'react';
import { Clock, TrendingUp, Users, DollarSign } from 'lucide-react';

const AuctionDashboard = () => {
  const [activeAuctions, setActiveAuctions] = useState([]);
  const [loading, setLoading] = useState(true);

  // Mock data for demonstration
  useEffect(() => {
    const mockAuctions = [
      {
        id: 1,
        commodity: 'Wheat',
        quantity: '500 Quintal',
        basePrice: 2000,
        currentBid: 2150,
        farmer: 'Ramesh Kumar',
        mandi: 'Delhi Mandi',
        endTime: new Date(Date.now() + 2 * 60 * 60 * 1000), // 2 hours from now
        bidsCount: 15,
        quality: 'A Grade'
      },
      {
        id: 2,
        commodity: 'Rice',
        quantity: '300 Quintal',
        basePrice: 1800,
        currentBid: 1950,
        farmer: 'Suresh Patel',
        mandi: 'Mumbai Mandi',
        endTime: new Date(Date.now() + 1.5 * 60 * 60 * 1000), // 1.5 hours from now
        bidsCount: 23,
        quality: 'B Grade'
      },
      {
        id: 3,
        commodity: 'Cotton',
        quantity: '200 Quintal',
        basePrice: 5000,
        currentBid: 5300,
        farmer: 'Mohan Singh',
        mandi: 'Ahmedabad Mandi',
        endTime: new Date(Date.now() + 3 * 60 * 60 * 1000), // 3 hours from now
        bidsCount: 8,
        quality: 'A Grade'
      }
    ];

    setTimeout(() => {
      setActiveAuctions(mockAuctions);
      setLoading(false);
    }, 1000);
  }, []);

  const formatTimeRemaining = (endTime) => {
    const now = new Date();
    const diff = endTime - now;
    const hours = Math.floor(diff / (1000 * 60 * 60));
    const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
    return `${hours}h ${minutes}m`;
  };

  if (loading) {
    return (
      <div className="container mx-auto px-4 py-8">
        <div className="text-center">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-green-600 mx-auto"></div>
          <p className="mt-4 text-gray-600">Loading active auctions...</p>
        </div>
      </div>
    );
  }

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900 mb-2">Live Auctions</h1>
        <p className="text-gray-600">Real-time bidding on agricultural commodities</p>
      </div>

      {/* Stats Cards */}
      <div className="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
        <div className="bg-white p-6 rounded-lg shadow-md">
          <div className="flex items-center">
            <Clock className="h-8 w-8 text-blue-600" />
            <div className="ml-4">
              <p className="text-sm font-medium text-gray-600">Active Auctions</p>
              <p className="text-2xl font-bold text-gray-900">{activeAuctions.length}</p>
            </div>
          </div>
        </div>
        <div className="bg-white p-6 rounded-lg shadow-md">
          <div className="flex items-center">
            <TrendingUp className="h-8 w-8 text-green-600" />
            <div className="ml-4">
              <p className="text-sm font-medium text-gray-600">Total Bids</p>
              <p className="text-2xl font-bold text-gray-900">
                {activeAuctions.reduce((sum, auction) => sum + auction.bidsCount, 0)}
              </p>
            </div>
          </div>
        </div>
        <div className="bg-white p-6 rounded-lg shadow-md">
          <div className="flex items-center">
            <Users className="h-8 w-8 text-purple-600" />
            <div className="ml-4">
              <p className="text-sm font-medium text-gray-600">Active Traders</p>
              <p className="text-2xl font-bold text-gray-900">42</p>
            </div>
          </div>
        </div>
        <div className="bg-white p-6 rounded-lg shadow-md">
          <div className="flex items-center">
            <DollarSign className="h-8 w-8 text-orange-600" />
            <div className="ml-4">
              <p className="text-sm font-medium text-gray-600">Avg Bid Value</p>
              <p className="text-2xl font-bold text-gray-900">₹2,133</p>
            </div>
          </div>
        </div>
      </div>

      {/* Active Auctions */}
      <div className="grid grid-cols-1 lg:grid-cols-2 xl:grid-cols-3 gap-6">
        {activeAuctions.map((auction) => (
          <div key={auction.id} className="bg-white rounded-lg shadow-md overflow-hidden">
            <div className="p-6">
              <div className="flex justify-between items-start mb-4">
                <div>
                  <h3 className="text-xl font-semibold text-gray-900">{auction.commodity}</h3>
                  <p className="text-sm text-gray-600">{auction.quality} • {auction.quantity}</p>
                </div>
                <span className="bg-green-100 text-green-800 text-xs font-medium px-2.5 py-0.5 rounded">
                  LIVE
                </span>
              </div>

              <div className="space-y-3">
                <div className="flex justify-between">
                  <span className="text-gray-600">Farmer:</span>
                  <span className="font-medium">{auction.farmer}</span>
                </div>
                <div className="flex justify-between">
                  <span className="text-gray-600">Mandi:</span>
                  <span className="font-medium">{auction.mandi}</span>
                </div>
                <div className="flex justify-between">
                  <span className="text-gray-600">Base Price:</span>
                  <span className="font-medium">₹{auction.basePrice}/quintal</span>
                </div>
                <div className="flex justify-between">
                  <span className="text-gray-600">Current Bid:</span>
                  <span className="font-bold text-green-600">₹{auction.currentBid}/quintal</span>
                </div>
                <div className="flex justify-between">
                  <span className="text-gray-600">Total Bids:</span>
                  <span className="font-medium">{auction.bidsCount}</span>
                </div>
                <div className="flex justify-between">
                  <span className="text-gray-600">Time Remaining:</span>
                  <span className="font-medium text-orange-600">
                    {formatTimeRemaining(auction.endTime)}
                  </span>
                </div>
              </div>

              <div className="mt-6 space-y-2">
                <button className="w-full bg-green-600 text-white py-2 px-4 rounded-lg hover:bg-green-700 transition duration-300">
                  Place Bid
                </button>
                <button className="w-full border border-gray-300 text-gray-700 py-2 px-4 rounded-lg hover:bg-gray-50 transition duration-300">
                  View Details
                </button>
              </div>
            </div>

            {/* Real-time indicator */}
            <div className="bg-gray-50 px-6 py-3">
              <div className="flex items-center justify-between text-sm">
                <span className="text-gray-600">Last bid:</span>
                <span className="text-gray-900">2 minutes ago</span>
              </div>
            </div>
          </div>
        ))}
      </div>

      {activeAuctions.length === 0 && (
        <div className="text-center py-12">
          <Clock className="h-12 w-12 text-gray-400 mx-auto mb-4" />
          <h3 className="text-lg font-medium text-gray-900 mb-2">No Active Auctions</h3>
          <p className="text-gray-600">Check back later for new commodity auctions.</p>
        </div>
      )}

      {/* WebSocket Connection Status */}
      <div className="fixed bottom-4 right-4">
        <div className="bg-green-100 border border-green-400 text-green-700 px-4 py-2 rounded flex items-center">
          <div className="w-2 h-2 bg-green-500 rounded-full mr-2 animate-pulse"></div>
          <span className="text-sm">Real-time updates connected</span>
        </div>
      </div>
    </div>
  );
};

export default AuctionDashboard;