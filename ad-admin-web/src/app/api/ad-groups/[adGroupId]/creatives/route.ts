import { NextRequest, NextResponse } from "next/server";
import { adGroups, creatives, nextCreativeId } from "@/lib/mock-store";
import { Creative } from "@/types/ad";

export async function GET(
  _request: NextRequest,
  { params }: { params: Promise<{ adGroupId: string }> },
) {
  const { adGroupId } = await params;
  const agId = Number(adGroupId);
  if (!adGroups.has(agId)) {
    return NextResponse.json(
      { error: "AdGroup not found" },
      { status: 404 },
    );
  }
  const list = Array.from(creatives.values()).filter(
    (c) => c.adGroupId === agId,
  );
  return NextResponse.json(list);
}

export async function POST(
  request: NextRequest,
  { params }: { params: Promise<{ adGroupId: string }> },
) {
  const { adGroupId } = await params;
  const agId = Number(adGroupId);
  if (!adGroups.has(agId)) {
    return NextResponse.json(
      { error: "AdGroup not found" },
      { status: 404 },
    );
  }
  const body = await request.json();
  if (!body.title || !body.clickUrl) {
    return NextResponse.json(
      { error: "title, clickUrl은 필수입니다." },
      { status: 400 },
    );
  }
  const id = nextCreativeId();
  const creative: Creative = {
    id,
    adGroupId: agId,
    title: body.title,
    description: body.description ?? null,
    imageUrl: body.imageUrl ?? null,
    clickUrl: body.clickUrl,
    status: "DRAFT",
  };
  creatives.set(id, creative);
  return NextResponse.json(creative, { status: 201 });
}
