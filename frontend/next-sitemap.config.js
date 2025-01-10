/** @type {import("next-sitemap").IConfig} */

const siteUrl = "https://ESAP/";

module.exports = {
  siteUrl,
  sourceDir: "build",
  generateRobotsTxt: true,
  robotsTxtOptions: {
    policies: [
      {
        userAgent: "*",
        allow: "/",
      },
    ],
  },
};
