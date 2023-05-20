const path = require('path');
var webpack = require('webpack');
var webpackMerge = require('webpack-merge');
//const UglifyJsPlugin = require('uglifyjs-webpack-plugin');

// caricamento della configurazione comune, che verra' integrata in
// questo file
var commonConfig = require('./webpack.common.js');

/// caricamento dei parametri di ambiente
const ENV = process.env.NODE_ENV = process.env.ENV = 'dev';
const ENV_PARAMS = require('./dev.properties.json');

/// merge della configurazione
module.exports = webpackMerge(commonConfig,{
  output: {
    "path": path.join(process.cwd(), "dist"),
    "publicPath": ENV_PARAMS.publicPath,
    "filename": "[name].bundle.js",
    "chunkFilename": "[id].chunk.js"
  },
  plugins: [
    new webpack.EnvironmentPlugin({
      NODE_ENV: 'dev', // use 'development' unless process.env.NODE_ENV is defined
      DEBUG: false
    }),
    
    new webpack.DefinePlugin({
      'ENV_PROPERTIES': JSON.stringify(ENV_PARAMS)
    }),

    new webpack.optimize.UglifyJsPlugin({
      include: /\.js$/,
      minimize: true
    })

  ],
  /*
   * Configurazione alternativa per Webpack 4
   */
  /*optimization: {
    minimize: true,
    minimizer: [new UglifyJsPlugin({
      include: /\.min\.js$/
    })]
  }*/


});