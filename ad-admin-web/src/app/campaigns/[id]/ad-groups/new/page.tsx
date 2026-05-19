"use client";

import { useRouter, useParams } from "next/navigation";
import { useState } from "react";
import { BidType } from "@/types/ad";

export default function NewAdGroupPage() {
  const router = useRouter();
  const params = useParams<{ id: string }>();
  const [name, setName] = useState("");
  const [bidType, setBidType] = useState<BidType>("CPC");
  const [bidAmount, setBidAmount] = useState("");
  const [error, setError] = useState("");

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    setError("");
    const res = await fetch(`/api/campaigns/${params.id}/ad-groups`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        name,
        bidType,
        bidAmount: Number(bidAmount),
      }),
    });
    if (!res.ok) {
      const data = await res.json();
      setError(data.error ?? "생성에 실패했습니다.");
      return;
    }
    router.push(`/campaigns/${params.id}`);
  }

  return (
    <div className="max-w-lg">
      <h1 className="text-xl font-bold mb-6">광고그룹 생성</h1>
      {error && <p className="text-red-600 text-sm mb-4">{error}</p>}
      <form onSubmit={handleSubmit} className="flex flex-col gap-4">
        <div>
          <label className="block text-sm font-medium mb-1">
            광고그룹명 *
          </label>
          <input
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
            className="w-full border rounded px-3 py-2 text-sm"
            placeholder="예: 20대 여성 타게팅"
          />
        </div>
        <div>
          <label className="block text-sm font-medium mb-1">입찰 방식 *</label>
          <select
            value={bidType}
            onChange={(e) => setBidType(e.target.value as BidType)}
            className="w-full border rounded px-3 py-2 text-sm"
          >
            <option value="CPC">CPC (클릭당 과금)</option>
            <option value="CPM">CPM (1,000 노출당 과금)</option>
          </select>
        </div>
        <div>
          <label className="block text-sm font-medium mb-1">입찰가 (원) *</label>
          <input
            type="number"
            value={bidAmount}
            onChange={(e) => setBidAmount(e.target.value)}
            required
            min="1"
            className="w-full border rounded px-3 py-2 text-sm"
            placeholder="예: 500"
          />
        </div>
        <div className="flex gap-2 mt-2">
          <button
            type="submit"
            className="px-4 py-2 bg-blue-600 text-white text-sm rounded hover:bg-blue-700"
          >
            생성
          </button>
          <button
            type="button"
            onClick={() => router.back()}
            className="px-4 py-2 border border-gray-300 text-sm rounded hover:bg-gray-50"
          >
            취소
          </button>
        </div>
      </form>
    </div>
  );
}
