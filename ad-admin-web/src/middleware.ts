import { NextRequest, NextResponse } from "next/server";

const publicPaths = ["/login", "/signup", "/find-account", "/find-password"];

export function middleware(request: NextRequest) {
  const { pathname } = request.nextUrl;

  // API, 정적 파일은 통과
  if (pathname.startsWith("/api") || pathname.startsWith("/_next")) {
    return NextResponse.next();
  }

  // 공개 페이지는 통과
  if (publicPaths.includes(pathname)) {
    return NextResponse.next();
  }

  // 세션 쿠키 확인
  const session = request.cookies.get("session");
  if (!session) {
    return NextResponse.redirect(new URL("/login", request.url));
  }

  return NextResponse.next();
}

export const config = {
  matcher: ["/((?!_next/static|_next/image|favicon.ico).*)"],
};
