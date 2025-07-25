import React from 'react';

const Dashboard = () => {
  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-8">Dashboard</h1>
      <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
        <div className="bg-white p-6 rounded-lg shadow-md">
          <h3 className="text-lg font-semibold mb-4">User Statistics</h3>
          <p className="text-gray-600">User activity and trading statistics will be displayed here.</p>
        </div>
        <div className="bg-white p-6 rounded-lg shadow-md">
          <h3 className="text-lg font-semibold mb-4">Recent Activities</h3>
          <p className="text-gray-600">Recent bids, listings, and transactions will appear here.</p>
        </div>
        <div className="bg-white p-6 rounded-lg shadow-md">
          <h3 className="text-lg font-semibold mb-4">Market Overview</h3>
          <p className="text-gray-600">Market trends and price analysis will be shown here.</p>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;