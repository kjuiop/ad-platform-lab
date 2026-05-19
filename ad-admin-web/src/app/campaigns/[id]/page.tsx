import Link from "next/link";
import { Campaign, AdGroup } from "@/types/ad";
import { StatusBadge } from "@/components/StatusBadge";
import { StatusActions } from "@/components/StatusActions";
import { DeleteCampaignButton } from "./DeleteCampaignButton";

async function getCampaign(id: string): Promise<Campaign> {
  const res = await fetch(`http://localhost:3001/api/campaigns/${id}`, {
    cache: "no-store",
  });
  if (!res.ok) throw new Error("Campaign not found");
  return res.json();
}

async function getAdGroups(campaignId: string): Promise<AdGroup[]> {
  const res = await fetch(
    `http://localhost:3001/api/campaigns/${campaignId}/ad-groups`,
    { cache: "no-store" },
  );
  return res.json();
}

export default async function CampaignDetailPage({
  params,
}: {
  params: Promise<{ id: string }>;
}) {
  const { id } = await params;
  const [campaign, adGroups] = await Promise.all([
    getCampaign(id),
    getAdGroups(id),
  ]);

  return (
    <div>
      {/* 캠페인 정보 */}
      <div className="flex items-center gap-3 mb-2">
        <h1 className="text-xl font-bold">{campaign.name}</h1>
        <StatusBadge status={campaign.status} />
      </div>
      <div className="text-sm text-gray-500 mb-4">
        {campaign.startDate ?? "시작일 미정"} ~ {campaign.endDate ?? "종료일 미정"}
      </div>
      <div className="flex gap-2 mb-8">
        <StatusActions
          status={campaign.status}
          apiUrl={`/api/campaigns/${id}/status`}
        />
        <Link
          href={`/campaigns/${id}/edit`}
          className="px-3 py-1 text-xs border border-gray-300 rounded hover:bg-gray-100"
        >
          수정
        </Link>
        <DeleteCampaignButton id={id} />
      </div>

      {/* 광고그룹 목록 */}
      <div className="border-t pt-6">
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-lg font-semibold">광고그룹</h2>
          <Link
            href={`/campaigns/${id}/ad-groups/new`}
            className="px-3 py-1.5 bg-blue-600 text-white text-sm rounded hover:bg-blue-700"
          >
            광고그룹 추가
          </Link>
        </div>

        {adGroups.length === 0 ? (
          <p className="text-gray-400 text-sm py-8 text-center">
            등록된 광고그룹이 없습니다.
          </p>
        ) : (
          <table className="w-full border-collapse">
            <thead>
              <tr className="border-b text-left text-sm text-gray-500">
                <th className="pb-3 w-16">ID</th>
                <th className="pb-3">광고그룹명</th>
                <th className="pb-3 w-20">입찰</th>
                <th className="pb-3 w-28">입찰가</th>
                <th className="pb-3 w-24">상태</th>
              </tr>
            </thead>
            <tbody>
              {adGroups.map((ag) => (
                <tr key={ag.id} className="border-b hover:bg-gray-50">
                  <td className="py-3 text-sm text-gray-500">{ag.id}</td>
                  <td className="py-3">
                    <Link
                      href={`/campaigns/${id}/ad-groups/${ag.id}`}
                      className="text-blue-600 hover:underline text-sm font-medium"
                    >
                      {ag.name}
                    </Link>
                  </td>
                  <td className="py-3 text-sm">{ag.bidType}</td>
                  <td className="py-3 text-sm">
                    {ag.bidAmount.toLocaleString()}원
                  </td>
                  <td className="py-3">
                    <StatusBadge status={ag.status} />
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>

      <div className="mt-6">
        <Link
          href="/campaigns"
          className="text-sm text-gray-500 hover:underline"
        >
          ← 캠페인 목록
        </Link>
      </div>
    </div>
  );
}
