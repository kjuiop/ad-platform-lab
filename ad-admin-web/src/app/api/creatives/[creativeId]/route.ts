import { NextRequest, NextResponse } from "next/server";
import { creatives } from "@/lib/mock-store";

export async function GET(
  _request: NextRequest,
  { params }: { params: Promise<{ creativeId: string }> },
) {
  const { creativeId } = await params;
  const creative = creatives.get(Number(creativeId));
  if (!creative) {
    return NextResponse.json({ error: "Not found" }, { status: 404 });
  }
  return NextResponse.json(creative);
}

export async function PUT(
  request: NextRequest,
  { params }: { params: Promise<{ creativeId: string }> },
) {
  const { creativeId } = await params;
  const creative = creatives.get(Number(creativeId));
  if (!creative) {
    return NextResponse.json({ error: "Not found" }, { status: 404 });
  }
  const body = await request.json();
  if (!body.title || !body.clickUrl) {
    return NextResponse.json(
      { error: "title, clickUrl은 필수입니다." },
      { status: 400 },
    );
  }
  creative.title = body.title;
  creative.description = body.description ?? null;
  creative.imageUrl = body.imageUrl ?? null;
  creative.clickUrl = body.clickUrl;
  return NextResponse.json(creative);
}

export async function DELETE(
  _request: NextRequest,
  { params }: { params: Promise<{ creativeId: string }> },
) {
  const { creativeId } = await params;
  if (!creatives.has(Number(creativeId))) {
    return NextResponse.json({ error: "Not found" }, { status: 404 });
  }
  creatives.delete(Number(creativeId));
  return new NextResponse(null, { status: 204 });
}
