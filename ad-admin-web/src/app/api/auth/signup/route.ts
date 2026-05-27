import { NextRequest, NextResponse } from "next/server";
import { getBackendUrl } from "@/lib/api";

export async function POST(request: NextRequest) {
  let body: unknown;
  try {
    body = await request.json();
  } catch {
    return NextResponse.json({ error: "잘못된 요청 형식입니다." }, { status: 400 });
  }

  const { name, email, password } = body as {
    name?: string;
    email?: string;
    password?: string;
  };

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

  try {
    const res = await fetch(`${getBackendUrl()}/api/auth/signup`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ name, email, password }),
    });

    const data = await res.json().catch(() => null);

    if (!res.ok) {
      return NextResponse.json(
        { error: data?.error || "회원가입에 실패했습니다." },
        { status: res.status },
      );
    }

    return NextResponse.json(data ?? { ok: true }, { status: 201 });
  } catch {
    return NextResponse.json(
      { error: "서버에 연결할 수 없습니다. 잠시 후 다시 시도해주세요." },
      { status: 502 },
    );
  }
}
