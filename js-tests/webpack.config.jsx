global.Promise = require('bluebird');

const webpack = require('webpack');
const path = require('path');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const {CleanWebpackPlugin} = require('clean-webpack-plugin');
const DuplicatePackageCheckerPlugin = require("duplicate-package-checker-webpack-plugin");
const HtmlWebpackPlugin = require('html-webpack-plugin');

const publicPath = path.resolve(__dirname, `public`);
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
    }),
    new HtmlWebpackPlugin({
        template: path.resolve(__dirname, "src/index.html"),
        minify: false,
        filename: 'index.html'
    })
];

if (devMode) {
    plugins.push(new webpack.HotModuleReplacementPlugin());
} else {
    plugins.push(new DuplicatePackageCheckerPlugin());
    plugins.push(new CleanWebpackPlugin({
        dry: false,
        verbose: true,
        cleanOnceBeforeBuildPatterns: [publicPath]
    }));
}

module.exports = {
    entry: {
        main: ["babel-polyfill", "./src/main/client"]
    },
    resolve: {
        roots: [path.resolve(__dirname, 'src')],
        modules: ['node_modules'],
        extensions: ['', '.js', '.jsx'],
        fallback: {
            "fs": false,
            "tls": false,
            "net": false,
            "path": false,
            "zlib": false,
            "http": false,
            "https": false,
            "stream": false,
            "crypto": false,
            "crypto-browserify": require.resolve('crypto-browserify')
        }
    },
    plugins,
    output: {
        path: publicPath,
        filename: jsName,
        publicPath: './'
    },
    module: {
        rules: [
            {
                test: /\.css$/,
                use: [
                    {loader: devMode ? 'style-loader' : MiniCssExtractPlugin.loader},
                    {loader: 'css-loader'}
                ]
            },
            {
                test: /\.js[x]?$/,
                use: 'babel-loader',
                exclude: [/node_modules/]
            },
        ]
    },
    devServer: {
        hot: true
    }
};