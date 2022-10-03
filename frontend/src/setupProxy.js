const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function (app) {
  app.use(
    '/api',
    createProxyMiddleware({
      target: 'http://localhost:8080',
      changeOrigin: false
    })
  );
  app.use(
    '/logout',
    createProxyMiddleware({
      target: 'http://localhost:8080',
      changeOrigin: false
    })
  );
  app.use(
    '/',
    createProxyMiddleware({
      target: 'http://localhost:8080',
      changeOrigin: false
    })
  );
};
