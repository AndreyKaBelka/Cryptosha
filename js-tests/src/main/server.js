import express from 'express';
import React from 'react';
import cookieParser from 'cookie-parser';
import path from 'path';
import {fileURLToPath} from 'url';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const PORT = process.env.PORT || 3000;

const app = express();
const buildPath = path.resolve(__dirname, "../../public");

app.use(cookieParser());
app.use(express.static(buildPath))

app.get('/', (req, res) => {
    res.sendFile(path.join(buildPath, 'index.html'))
})

app.listen(PORT, () => {
    console.log(`Server is listening on: ${PORT}`);
})