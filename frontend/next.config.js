module.exports = {
  basePath: process.env.BASE_PATH || "",
  distDir: "build",
  poweredByHeader: false,
  reactStrictMode: false, // TODO: BoxWelcomeCarousel gives an error, react-spring doesn't work at all...
  async headers() {
    return [
      {
        // Allow cross-origin requests from any origin
        // You can customize this with a whitelist of allowed origins
        source: "/api/:path*",
        headers: [
          {
            key: "Cache-Control",
            value: "no-cache",
          },
          {
            key: "Access-Control-Allow-Origin",
            value: "*",
          },
          {
            key: "Access-Control-Allow-Methods",
            value: "GET,OPTIONS,PATCH,DELETE,POST,PUT",
          },
          {
            key: "Access-Control-Allow-Headers",
            value: "X-Requested-With,Content-Type",
          },
        ],
      },
    ];
  },
  async redirects() {
    return [
      {
        source: "/404",
        destination: "/not-found",
        permanent: false,

      },
    ];
  },
  env: {
    API_URL: process.env.API_URL,
    AUTH_GOOGLE_ID: process.env.AUTH_GOOGLE_ID,
    AUTH_GOOGLE_SECRET: process.env.AUTH_GOOGLE_SECRET,
    AUTH_SECRET: process.env.AUTH_SECRET,
    BRANCH_NAME: process.env.BRANCH_NAME,
    JWT_KEY: process.env.JWT_KEY,
    MONGODB_URI: process.env.MONGODB_URI,
    NEXTAUTH_URL: process.env.NEXTAUTH_URL,
    PATH_UPLOADS: process.env.PATH_UPLOADS,
    TOKEN_SECRET: process.env.TOKEN_SECRET,
  },
};
