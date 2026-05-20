import { NextRequest, NextResponse } from "next/server";
import { mockUsers } from "@/lib/mock-users";

export async function POST(request: NextRequest) {
  const { name, email, password } = await request.json();

  if (!name || !email || !password) {
    return NextResponse.json(
      { error: "모든 항목을 입력해주세요." },
      { status: 400 },
    );
  }

  if (password.length < 8) {
    return NextResponse.json(
      { error: "비밀번호는 8자 이상이어야 합니다." },
      { status: 400 },
    );
  }

  const exists = mockUsers.find((u) => u.email === email);
  if (exists) {
    return NextResponse.json(
      { error: "이미 등록된 이메일입니다." },
      { status: 409 },
    );
  }

  // Mock: 실제로 저장하지 않고 성공 응답만 반환
  return NextResponse.json({ ok: true }, { status: 201 });
}
