import { NextRequest, NextResponse } from "next/server";
import { adGroups } from "@/lib/mock-store";
import { AdStatus } from "@/types/ad";

const validTransitions: Record<AdStatus, AdStatus[]> = {
  DRAFT: ["ACTIVE"],
  ACTIVE: ["PAUSED", "COMPLETED"],
  PAUSED: ["ACTIVE", "COMPLETED"],
  COMPLETED: [],
};

export async function PATCH(
  request: NextRequest,
  { params }: { params: Promise<{ adGroupId: string }> },
) {
  const { adGroupId } = await params;
  const adGroup = adGroups.get(Number(adGroupId));
  if (!adGroup) {
    return NextResponse.json({ error: "Not found" }, { status: 404 });
  }
  const { status } = await request.json();
  if (!validTransitions[adGroup.status]?.includes(status)) {
    return NextResponse.json(
      {
        error: `${adGroup.status}에서 ${status}로 변경할 수 없습니다.`,
      },
      { status: 400 },
    );
  }
  adGroup.status = status;
  return NextResponse.json(adGroup);
}
