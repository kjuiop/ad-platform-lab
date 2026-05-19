import { NextRequest, NextResponse } from "next/server";
import {
  campaigns,
  adGroups,
  nextAdGroupId,
} from "@/lib/mock-store";
import { AdGroup } from "@/types/ad";

export async function GET(
  _request: NextRequest,
  { params }: { params: Promise<{ id: string }> },
) {
  const { id } = await params;
  const campaignId = Number(id);
  if (!campaigns.has(campaignId)) {
    return NextResponse.json({ error: "Campaign not found" }, { status: 404 });
  }
  const list = Array.from(adGroups.values()).filter(
    (ag) => ag.campaignId === campaignId,
  );
  return NextResponse.json(list);
}

export async function POST(
  request: NextRequest,
  { params }: { params: Promise<{ id: string }> },
) {
  const { id } = await params;
  const campaignId = Number(id);
  if (!campaigns.has(campaignId)) {
    return NextResponse.json({ error: "Campaign not found" }, { status: 404 });
  }
  const body = await request.json();
  if (!body.name || !body.bidType || body.bidAmount == null) {
    return NextResponse.json(
      { error: "name, bidType, bidAmount는 필수입니다." },
      { status: 400 },
    );
  }
  const agId = nextAdGroupId();
  const adGroup: AdGroup = {
    id: agId,
    campaignId,
    name: body.name,
    bidType: body.bidType,
    bidAmount: body.bidAmount,
    status: "DRAFT",
  };
  adGroups.set(agId, adGroup);
  return NextResponse.json(adGroup, { status: 201 });
}
