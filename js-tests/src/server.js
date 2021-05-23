import express from 'express';
import React from 'react';
import ReactDom from 'react-dom/server';
import App from './components/App';
import cookieParser from 'cookie-parser';
const PORT = process.env.PORT || 3001;
// const assetUrl = process.env.NODE_ENV !== 'production' ? `http://localhost:${PORT}` : '/';

const app = express();

app.use(cookieParser());

function renderToHtml(componentHTML) {
    return `
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta
                name="viewport"
                content="width=device-width, initial-scale=1, shrink-to-fit=no"
        />
        <script src="https://fb.me/react-0.12.2.js"></script>
        <script src=https://fb.me/JSXTransformer-0.12.2.jss"></script>
        <script src="https://code.jquery.com/jquery-1.10.0.min.js"> </script>
        <meta name="theme-color" content="#000000" />
        <title>Messages</title>
    </head>
    <body>
    <div id="root">${componentHTML}</div>
        
    </body>
    </html>
`;
}

app.use((req, res) => {
        const componentHTML = ReactDom.renderToString(<App showMessages={false}/>);

        return res.send(renderToHtml(componentHTML));
    }
)


app.listen(PORT, () => {
    console.log(`Server is listening on: ${PORT}`);
})