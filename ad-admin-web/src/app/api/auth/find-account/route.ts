import { NextRequest, NextResponse } from "next/server";
import { mockUsers } from "@/lib/mock-users";

export async function POST(request: NextRequest) {
  const { name, phone } = await request.json();

  if (!name || !phone) {
    return NextResponse.json(
      { error: "이름과 연락처를 모두 입력해주세요." },
      { status: 400 },
    );
  }

  const found = mockUsers.find(
    (u) => u.user.name === name && u.phone === phone,
  );
  if (!found) {
    return NextResponse.json(
      { error: "입력하신 정보와 일치하는 계정을 찾을 수 없습니다." },
      { status: 404 },
    );
  }

  // Mock: 이메일 일부를 마스킹해서 반환
  const email = found.email;
  const [local, domain] = email.split("@");
  const masked = local.slice(0, 2) + "***@" + domain;

  return NextResponse.json({ email: masked });
}
