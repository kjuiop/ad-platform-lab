import Link from "next/link";
import { Campaign } from "@/types/ad";
import { StatusBadge } from "@/components/StatusBadge";

async function getCampaigns(): Promise<Campaign[]> {
  const res = await fetch("http://localhost:3001/api/campaigns", {
    cache: "no-store",
  });
  return res.json();
}

export default async function CampaignsPage() {
  const campaigns = await getCampaigns();

  return (
    <div>
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-xl font-bold">캠페인 관리</h1>
        <Link
          href="/campaigns/new"
          className="px-4 py-2 bg-blue-600 text-white text-sm rounded hover:bg-blue-700"
        >
          캠페인 생성
        </Link>
      </div>

      {campaigns.length === 0 ? (
        <div className="text-center py-16 text-gray-400">
          <p className="mb-4">등록된 캠페인이 없습니다.</p>
          <Link
            href="/campaigns/new"
            className="text-blue-600 hover:underline text-sm"
          >
            첫 캠페인 만들기
          </Link>
        </div>
      ) : (
        <table className="w-full border-collapse">
          <thead>
            <tr className="border-b text-left text-sm text-gray-500">
              <th className="pb-3 w-16">ID</th>
              <th className="pb-3">캠페인명</th>
              <th className="pb-3 w-24">상태</th>
              <th className="pb-3 w-32">시작일</th>
              <th className="pb-3 w-32">종료일</th>
            </tr>
          </thead>
          <tbody>
            {campaigns.map((campaign) => (
              <tr key={campaign.id} className="border-b hover:bg-gray-50">
                <td className="py-3 text-sm text-gray-500">{campaign.id}</td>
                <td className="py-3">
                  <Link
                    href={`/campaigns/${campaign.id}`}
                    className="text-blue-600 hover:underline text-sm font-medium"
                  >
                    {campaign.name}
                  </Link>
                </td>
                <td className="py-3">
                  <StatusBadge status={campaign.status} />
                </td>
                <td className="py-3 text-sm text-gray-600">
                  {campaign.startDate ?? "-"}
                </td>
                <td className="py-3 text-sm text-gray-600">
                  {campaign.endDate ?? "-"}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}
