import { NextRequest, NextResponse } from "next/server";
import { mockUsers } from "@/lib/mock-users";

export async function POST(request: NextRequest) {
  const { email } = await request.json();

  const found = mockUsers.find((u) => u.email === email);
  if (!found) {
    return NextResponse.json(
      { error: "등록되지 않은 이메일입니다." },
      { status: 404 },
    );
  }

  // Mock: 실제로 이메일을 보내지 않고 성공 응답만 반환
  return NextResponse.json({ ok: true });
}
