import { NextRequest, NextResponse } from "next/server";

const publicPaths = ["/login", "/signup", "/find-account", "/find-password"];

export function proxy(request: NextRequest) {
  const { pathname } = request.nextUrl;

  // 정적 파일은 통과
  if (pathname.startsWith("/_next")) {
    return NextResponse.next();
  }

  // 인증 관련 API는 공개
  if (pathname.startsWith("/api/auth/")) {
    return NextResponse.next();
  }

  // 그 외 API는 세션 쿠키 필수
  if (pathname.startsWith("/api")) {
    const session = request.cookies.get("session");
    if (!session) {
      return NextResponse.json(
        { error: "인증이 필요합니다." },
        { status: 401 },
      );
    }
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
