import { NextRequest, NextResponse } from "next/server";
import { campaigns, adGroups, creatives } from "@/lib/mock-store";

export async function GET(
  _request: NextRequest,
  { params }: { params: Promise<{ id: string }> },
) {
  const { id } = await params;
  const campaign = campaigns.get(Number(id));
  if (!campaign) {
    return NextResponse.json({ error: "Not found" }, { status: 404 });
  }
  return NextResponse.json(campaign);
}

export async function PUT(
  request: NextRequest,
  { params }: { params: Promise<{ id: string }> },
) {
  const { id } = await params;
  const campaign = campaigns.get(Number(id));
  if (!campaign) {
    return NextResponse.json({ error: "Not found" }, { status: 404 });
  }
  const body = await request.json();
  if (!body.name) {
    return NextResponse.json(
      { error: "name은 필수입니다." },
      { status: 400 },
    );
  }
  const isoDateRegex = /^\d{4}-\d{2}-\d{2}$/;
  for (const field of ["startDate", "endDate"] as const) {
    if (body[field] != null) {
      if (!isoDateRegex.test(body[field]) || isNaN(Date.parse(body[field]))) {
        return NextResponse.json(
          { error: `${field}은(는) YYYY-MM-DD 형식이어야 합니다.` },
          { status: 400 },
        );
      }
    }
  }
  if (body.startDate && body.endDate && body.startDate > body.endDate) {
    return NextResponse.json(
      { error: "startDate는 endDate보다 이전이어야 합니다." },
      { status: 400 },
    );
  }
  campaign.name = body.name;
  campaign.startDate = body.startDate ?? null;
  campaign.endDate = body.endDate ?? null;
  return NextResponse.json(campaign);
}

export async function DELETE(
  _request: NextRequest,
  { params }: { params: Promise<{ id: string }> },
) {
  const { id } = await params;
  if (!campaigns.has(Number(id))) {
    return NextResponse.json({ error: "Not found" }, { status: 404 });
  }
  const campaignId = Number(id);
  const childAdGroupIds: number[] = [];
  for (const [agId, ag] of adGroups) {
    if (ag.campaignId === campaignId) childAdGroupIds.push(agId);
  }
  for (const [crId, cr] of creatives) {
    if (childAdGroupIds.includes(cr.adGroupId)) creatives.delete(crId);
  }
  for (const agId of childAdGroupIds) {
    adGroups.delete(agId);
  }
  campaigns.delete(campaignId);
  return new NextResponse(null, { status: 204 });
}
