
const { Pool } = require('pg');

// Create a new pool instance. The pool will read the DATABASE_URL environment variable
// automatically, which is perfect for Render's managed PostgreSQL service.
const pool = new Pool({
    connectionString: process.env.DATABASE_URL,
    ssl: process.env.NODE_ENV === 'production' ? { rejectUnauthorized: false } : false,
});

// Function to initialize the database tables if they don't exist.
const initDb = async () => {
    const createDevicesTable = `
        CREATE TABLE IF NOT EXISTS devices (
            id SERIAL PRIMARY KEY,
            device_id VARCHAR(255) UNIQUE NOT NULL,
            model VARCHAR(100),
            registered_at TIMESTAMPTZ DEFAULT NOW()
        );
    `;
    const createDatalogsTable = `
        CREATE TABLE IF NOT EXISTS datalogs (
            id SERIAL PRIMARY KEY,
            device_id VARCHAR(255) NOT NULL,
            type VARCHAR(50) NOT NULL, -- e.g., 'location', 'text', 'call'
            content TEXT NOT NULL,
            created_at TIMESTAMPTZ DEFAULT NOW(),
            FOREIGN KEY (device_id) REFERENCES devices(device_id)
        );
    `;
    try {
        await pool.query(createDevicesTable);
        await pool.query(createDatalogsTable);
        console.log('Database tables checked/created successfully.');
    } catch (err) {
        console.error('Error initializing database:', err);
        process.exit(1); // Exit if DB initialization fails
    }
};

// Export a query function to be used by the rest of the application
module.exports = {
    query: (text, params) => pool.query(text, params),
    initDb,
};
