import { NextRequest, NextResponse } from "next/server";
import { adGroups, creatives } from "@/lib/mock-store";

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
  const validBidTypes = ["CPC", "CPM"];
  if (
    !body.name ||
    !validBidTypes.includes(body.bidType) ||
    !Number.isFinite(body.bidAmount) ||
    body.bidAmount <= 0
  ) {
    return NextResponse.json(
      { error: "name은 필수, bidType은 CPC/CPM, bidAmount는 0보다 큰 숫자여야 합니다." },
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
  const agId = Number(adGroupId);
  for (const [crId, cr] of creatives) {
    if (cr.adGroupId === agId) creatives.delete(crId);
  }
  adGroups.delete(agId);
  return new NextResponse(null, { status: 204 });
}
