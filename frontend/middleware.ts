import { NextResponse } from "next/server";
import acceptLanguage from "accept-language";
import { cookieName, fallbackLng, languages } from "./app/i18n/settings";

acceptLanguage.languages(languages);

export const config = {
  // matcher: '/:lng*'
  matcher: ["/((?!api|_next/static|_next/image|assets|favicon.ico|sw.js).*)"],
};

export function middleware(request: any) {
  // Skip for public assets
  const PUBLIC_FILE = /\.(.*)$/;
  if (request.nextUrl.pathname.startsWith("/_next") || PUBLIC_FILE.test(request.nextUrl.pathname)) {
    return;
  }

  // Get locale
  let lng;
  if (request.cookies.has(cookieName)) lng = acceptLanguage.get(request.cookies.get(cookieName).value);
  if (!lng) lng = acceptLanguage.get(request.headers.get("Accept-Language"));
  if (!lng) lng = fallbackLng;

  // Redirect if lng in path is not supported
  if (!languages.some((loc: any) => request.nextUrl.pathname.startsWith(`/${loc}`)) && !request.nextUrl.pathname.startsWith("/_next")) {
    return NextResponse.redirect(new URL(`/${lng}${request.nextUrl.pathname}${request.nextUrl.search}`, request.url));
  }

  if (request.headers.has("referer")) {
    const refererUrl = new URL(request.headers.get("referer"));
    const lngInReferer = languages.find((l: any) => refererUrl.pathname.startsWith(`/${l}`));
    const response = NextResponse.next();
    if (lngInReferer) response.cookies.set(cookieName, lngInReferer);
    return response;
  }

  /* ================================================================================================================ */
  /* BASIC AUTH - START                                                                                               */
  /* ================================================================================================================ */

  // Getting the Pup IP from the request
  const basicAuth = request.headers.get("authorization");
  const ip = request.ip;
  const url = request.nextUrl;

  // console.log("Middleware IP:", ip);

  // Bypass the basic auth on a certain env variable and Pub IP
  if (process.env.BASIC_AUTH === "YES") {
    if (basicAuth) {
      const authValue = basicAuth.split(" ")[1];
      const [user, pwd] = atob(authValue).split(":");

      const validUser = process.env.BASIC_AUTH_USER;
      const validPassWord = process.env.BASIC_AUTH_PASSWORD;

      if (user === validUser && pwd === validPassWord) {
        return NextResponse.next();
      }
    }
    url.pathname = "/api/basicauth";

    return NextResponse.rewrite(url);
  }

  /* ================================================================================================================ */
  /* BASIC AUTH - END                                                                                                 */
  /* ================================================================================================================ */

  // Add a new header "x-current-path" which passes the path to downstream components
  const requestHeaders = new Headers(request.headers);
  const xCurrentPath = request.nextUrl.pathname?.replace(`/${lng}/`, "")?.replaceAll("/", "-");
  requestHeaders.set("x-current-path", xCurrentPath);

  return NextResponse.next({
    headers: requestHeaders,
    request: {
      headers: requestHeaders,
    },
  });
}
