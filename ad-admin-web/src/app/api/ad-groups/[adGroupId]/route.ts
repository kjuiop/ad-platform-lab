import { NextRequest, NextResponse } from "next/server";
import { adGroups } from "@/lib/mock-store";

export async function GET(
  _request: NextRequest,
  { params }: { params: Promise<{ adGroupId: string }> },
) {
  const { adGroupId } = await params;
  const adGroup = adGroups.get(Number(adGroupId));
  if (!adGroup) {
    return NextResponse.json({ error: "Not found" }, { status: 404 });
  }
  return NextResponse.json(adGroup);
}

export async function PUT(
  request: NextRequest,
  { params }: { params: Promise<{ adGroupId: string }> },
) {
  const { adGroupId } = await params;
  const adGroup = adGroups.get(Number(adGroupId));
  if (!adGroup) {
    return NextResponse.json({ error: "Not found" }, { status: 404 });
  }
  const body = await request.json();
  if (!body.name || !body.bidType || body.bidAmount == null) {
    return NextResponse.json(
      { error: "name, bidType, bidAmount는 필수입니다." },
      { status: 400 },
    );
  }
  adGroup.name = body.name;
  adGroup.bidType = body.bidType;
  adGroup.bidAmount = body.bidAmount;
  return NextResponse.json(adGroup);
}

export async function DELETE(
  _request: NextRequest,
  { params }: { params: Promise<{ adGroupId: string }> },
) {
  const { adGroupId } = await params;
  if (!adGroups.has(Number(adGroupId))) {
    return NextResponse.json({ error: "Not found" }, { status: 404 });
  }
  adGroups.delete(Number(adGroupId));
  return new NextResponse(null, { status: 204 });
}
