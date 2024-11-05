export async function GET(request: Request) {

  // console.log("GET /api/basicauth/route.ts");

  return new Response("Authentication Required!", {
    status: 401,
    headers: {
      "WWW-Authenticate": "Basic realm='private_pages'",
    },
  });
}
