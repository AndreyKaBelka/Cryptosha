global.Promise = require('bluebird');

const webpack = require('webpack');
const path = require('path');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const {CleanWebpackPlugin} = require('clean-webpack-plugin');
const DuplicatePackageCheckerPlugin = require("duplicate-package-checker-webpack-plugin");

const publicPath = `http://localhost:${process.env.PORT || '3001'}/public/assets`;
const jsName = process.env.NODE_ENV === 'production' ? 'bundle-[hash].js' : 'bundle.js';
const devMode = process.env.NODE_ENV !== 'production';

const plugins = [
    new webpack.DefinePlugin({
        'process.env': {
            BROWSER: JSON.stringify(true),
            NODE_ENV: JSON.stringify(process.env.NODE_ENV || 'development')
        }
    }),
    new MiniCssExtractPlugin({
        filename: devMode ? '[name].css' : '[name].[contenthash].css',
        chunkFilename: devMode ? '[id].css' : '[id].[contenthash].css',
    }),
    new webpack.LoaderOptionsPlugin({
        debug: true
    })
];

if (devMode) {
    plugins.push(new webpack.HotModuleReplacementPlugin());
}

if (!devMode) {
    plugins.push(new CleanWebpackPlugin({
        dry: false,
        verbose: true,
        cleanAfterEveryBuildPatterns: [__dirname + "/public/assets/"]
    }))
    plugins.push(new DuplicatePackageCheckerPlugin());
}

module.exports = {
    entry: ['babel-polyfill', './src/client.js'],
    resolve: {
        roots: [path.join(__dirname, 'src')],
        modules: ['node_modules'],
        extensions: ['', '.js', '.jsx']
    },
    plugins,
    output: {
        path: `${__dirname}/public/assets/`,
        filename: jsName,
        publicPath
    },
    module: {
        rules: [
            {
                test: /\.css$/,
                use: [MiniCssExtractPlugin.loader, 'css-loader']
            },
            {test: /\.gif$/, use: 'url-loader?limit=10000&mimetype=image/gif'},
            {test: /\.jpg$/, use: 'url-loader?limit=10000&mimetype=image/jpg'},
            {test: /\.png$/, use: 'url-loader?limit=10000&mimetype=image/png'},
            {test: /\.svg/, use: 'url-loader?limit=26000&mimetype=image/svg+xml'},
            {test: /\.(woff|woff2|ttf|eot)/, use: 'url-loader?limit=1'},
            { test: /\.jsx?$/, use: 'babel-loader', exclude: [/node_modules/, /public/]},
            {test: /\.json$/, use: 'json-loader'},
        ]
    },
    devtool: devMode ? 'source-map' : null,
    devServer: {
        headers: {'Access-Control-Allow-Origin': '*'}
    }
};