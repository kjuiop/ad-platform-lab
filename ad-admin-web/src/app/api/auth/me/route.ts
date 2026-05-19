import { NextRequest, NextResponse } from "next/server";
import { mockUsers } from "@/lib/mock-users";

export async function GET(request: NextRequest) {
  const sessionId = request.cookies.get("session")?.value;

  if (!sessionId) {
    return NextResponse.json({ error: "Unauthorized" }, { status: 401 });
  }

  const found = mockUsers.find((u) => u.user.id === Number(sessionId));

  if (!found) {
    return NextResponse.json({ error: "Unauthorized" }, { status: 401 });
  }

  return NextResponse.json(found.user);
}
