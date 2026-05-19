import { NextRequest, NextResponse } from "next/server";
import { mockUsers } from "@/lib/mock-users";

export async function POST(request: NextRequest) {
  const { name } = await request.json();

  const found = mockUsers.find((u) => u.user.name === name);
  if (!found) {
    return NextResponse.json(
      { error: "일치하는 계정을 찾을 수 없습니다." },
      { status: 404 },
    );
  }

  // Mock: 이메일 일부를 마스킹해서 반환
  const email = found.email;
  const [local, domain] = email.split("@");
  const masked = local.slice(0, 2) + "***@" + domain;

  return NextResponse.json({ email: masked });
}
