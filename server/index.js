
const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const db = require('./db');

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware
app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// API Endpoints

// [POST] Register a new device
app.post('/api/register', async (req, res) => {
    const { deviceId, model } = req.body;
    if (!deviceId || !model) {
        return res.status(400).json({ error: 'deviceId and model are required' });
    }
    const text = 'INSERT INTO devices(device_id, model) VALUES($1, $2) ON CONFLICT (device_id) DO NOTHING RETURNING *';
    const values = [deviceId, model];
    try {
        const result = await db.query(text, values);
        console.log(`Device registered or already exists: ${deviceId}`);
        res.status(201).json(result.rows[0] || { message: 'Device already registered' });
    } catch (err) {
        console.error('Error registering device:', err);
        res.status(500).json({ error: 'Internal server error' });
    }
});

// [POST] Receive data from a device
app.post('/api/data', async (req, res) => {
    const { deviceId, type, content } = req.body;
    if (!deviceId || !type || !content) {
        return res.status(400).json({ error: 'deviceId, type, and content are required' });
    }
    const text = 'INSERT INTO datalogs(device_id, type, content) VALUES($1, $2, $3) RETURNING id';
    const values = [deviceId, type, content];
    try {
        const result = await db.query(text, values);
        console.log(`Received data of type '${type}' from device ${deviceId}`);
        res.status(201).json({ message: 'Data received', dataId: result.rows[0].id });
    } catch (err) {
        console.error('Error receiving data:', err);
        res.status(500).json({ error: 'Internal server error' });
    }
});

// [GET] Get all registered devices
app.get('/api/devices', async (req, res) => {
    try {
        const result = await db.query('SELECT device_id, model, registered_at FROM devices ORDER BY registered_at DESC');
        res.status(200).json(result.rows);
    } catch (err) {
        console.error('Error fetching devices:', err);
        res.status(500).json({ error: 'Internal server error' });
    }
});

// [GET] Get all data logs for a specific device
app.get('/api/data/:deviceId', async (req, res) => {
    const { deviceId } = req.params;
    try {
        const result = await db.query('SELECT type, content, created_at FROM datalogs WHERE device_id = $1 ORDER BY created_at DESC', [deviceId]);
        res.status(200).json(result.rows);
    } catch (err) {
        console.error(`Error fetching data for device ${deviceId}:`, err);
        res.status(500).json({ error: 'Internal server error' });
    }
});

// Default route
app.get('/', (req, res) => {
    res.send('MySuperProject Server is running!');
});

// Start the server after initializing the database
const startServer = async () => {
    await db.initDb();
    app.listen(PORT, () => {
        console.log(`Server is running on port ${PORT}`);
    });
};

startServer();
