// Simple educational server (non-malicious). Receives simulated device registration and commands.
const express = require('express');
const app = express();
app.use(express.json());

// in-memory store for demo purposes
const devices = {};

app.post('/api/register', (req, res) => {
  const { deviceId, info } = req.body || {};
  if (!deviceId) return res.status(400).json({ error: 'deviceId required' });
  devices[deviceId] = { info: info || {}, lastSeen: new Date().toISOString() };
  console.log('[SERVER] Device registered:', deviceId);
  res.json({ success: true, deviceId });
});

app.get('/api/devices', (req, res) => {
  res.json(devices);
});

app.post('/api/command', (req, res) => {
  const { deviceId, command } = req.body || {};
  console.log('[SERVER] Received fake command for device', deviceId, 'command=', command);
  // For safety: server does NOT forward any harmful commands. It's a simulation.
  res.json({ success: true, note: 'Command received (simulated).' });
});

app.listen(3000, () => console.log('Fake lab server running on http://localhost:3000'));
