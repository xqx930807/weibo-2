connect = require 'connect'
serveStatic = require 'serve-static'
connect().use(serveStatic(__dirname)).listen 3000

console.log 'http-server is running at port 3000....'