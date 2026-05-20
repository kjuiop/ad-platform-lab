import { NextRequest, NextResponse } from "next/server";

export async function POST(request: NextRequest) {
  const { email } = await request.json();

  if (!email) {
    return NextResponse.json(
      { error: "이메일을 입력해주세요." },
      { status: 400 },
    );
  }

  // Mock: 계정 존재 여부와 관계없이 동일한 성공 응답 반환 (계정 열거 방지)
  return NextResponse.json({ ok: true });
}
