import Link from "next/link";
import { AdGroup, Creative } from "@/types/ad";
import { StatusBadge } from "@/components/StatusBadge";
import { StatusActions } from "@/components/StatusActions";
import { DeleteAdGroupButton } from "./DeleteAdGroupButton";

async function getAdGroup(adGroupId: string): Promise<AdGroup> {
  const res = await fetch(
    `http://localhost:3001/api/ad-groups/${adGroupId}`,
    { cache: "no-store" },
  );
  if (!res.ok) throw new Error("AdGroup not found");
  return res.json();
}

async function getCreatives(adGroupId: string): Promise<Creative[]> {
  const res = await fetch(
    `http://localhost:3001/api/ad-groups/${adGroupId}/creatives`,
    { cache: "no-store" },
  );
  return res.json();
}

export default async function AdGroupDetailPage({
  params,
}: {
  params: Promise<{ id: string; adGroupId: string }>;
}) {
  const { id, adGroupId } = await params;
  const [adGroup, creatives] = await Promise.all([
    getAdGroup(adGroupId),
    getCreatives(adGroupId),
  ]);

  return (
    <div>
      {/* 광고그룹 정보 */}
      <div className="flex items-center gap-3 mb-2">
        <h1 className="text-xl font-bold">{adGroup.name}</h1>
        <StatusBadge status={adGroup.status} />
      </div>
      <div className="text-sm text-gray-500 mb-4">
        {adGroup.bidType} · {adGroup.bidAmount.toLocaleString()}원
      </div>
      <div className="flex gap-2 mb-8">
        <StatusActions
          status={adGroup.status}
          apiUrl={`/api/ad-groups/${adGroupId}/status`}
        />
        <Link
          href={`/campaigns/${id}/ad-groups/${adGroupId}/edit`}
          className="px-3 py-1 text-xs border border-gray-300 rounded hover:bg-gray-100"
        >
          수정
        </Link>
        <DeleteAdGroupButton campaignId={id} adGroupId={adGroupId} />
      </div>

      {/* 소재 목록 */}
      <div className="border-t pt-6">
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-lg font-semibold">소재</h2>
          <Link
            href={`/campaigns/${id}/ad-groups/${adGroupId}/creatives/new`}
            className="px-3 py-1.5 bg-blue-600 text-white text-sm rounded hover:bg-blue-700"
          >
            소재 추가
          </Link>
        </div>

        {creatives.length === 0 ? (
          <p className="text-gray-400 text-sm py-8 text-center">
            등록된 소재가 없습니다.
          </p>
        ) : (
          <table className="w-full border-collapse">
            <thead>
              <tr className="border-b text-left text-sm text-gray-500">
                <th className="pb-3 w-16">ID</th>
                <th className="pb-3">제목</th>
                <th className="pb-3">설명</th>
                <th className="pb-3 w-24">상태</th>
                <th className="pb-3 w-16"></th>
              </tr>
            </thead>
            <tbody>
              {creatives.map((c) => (
                <tr key={c.id} className="border-b hover:bg-gray-50">
                  <td className="py-3 text-sm text-gray-500">{c.id}</td>
                  <td className="py-3 text-sm font-medium">{c.title}</td>
                  <td className="py-3 text-sm text-gray-600 truncate max-w-xs">
                    {c.description ?? "-"}
                  </td>
                  <td className="py-3">
                    <StatusBadge status={c.status} />
                  </td>
                  <td className="py-3">
                    <Link
                      href={`/campaigns/${id}/ad-groups/${adGroupId}/creatives/${c.id}/edit`}
                      className="text-xs text-blue-600 hover:underline"
                    >
                      수정
                    </Link>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>

      <div className="mt-6">
        <Link
          href={`/campaigns/${id}`}
          className="text-sm text-gray-500 hover:underline"
        >
          ← 캠페인 상세
        </Link>
      </div>
    </div>
  );
}
