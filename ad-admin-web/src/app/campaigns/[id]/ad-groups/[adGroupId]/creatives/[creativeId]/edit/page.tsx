"use client";

import { useRouter, useParams } from "next/navigation";
import { useState, useEffect } from "react";
import { Creative } from "@/types/ad";
import { CreativePreview } from "@/components/CreativePreview";

export default function EditCreativePage() {
  const router = useRouter();
  const params = useParams<{
    id: string;
    adGroupId: string;
    creativeId: string;
  }>();
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [imageUrl, setImageUrl] = useState("");
  const [clickUrl, setClickUrl] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch(`/api/creatives/${params.creativeId}`)
      .then((res) => res.json())
      .then((data: Creative) => {
        setTitle(data.title);
        setDescription(data.description ?? "");
        setImageUrl(data.imageUrl ?? "");
        setClickUrl(data.clickUrl);
        setLoading(false);
      });
  }, [params.creativeId]);

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    setError("");
    const res = await fetch(`/api/creatives/${params.creativeId}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        title,
        description: description || null,
        imageUrl: imageUrl || null,
        clickUrl,
      }),
    });
    if (!res.ok) {
      const data = await res.json();
      setError(data.error ?? "수정에 실패했습니다.");
      return;
    }
    router.push(
      `/campaigns/${params.id}/ad-groups/${params.adGroupId}`,
    );
  }

  if (loading) {
    return <p className="text-gray-400 text-sm">로딩 중...</p>;
  }

  return (
    <div className="flex gap-8">
      <div className="max-w-lg flex-1">
        <h1 className="text-xl font-bold mb-6">소재 수정</h1>
        {error && <p className="text-red-600 text-sm mb-4">{error}</p>}
        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
          <div>
            <label className="block text-sm font-medium mb-1">제목 *</label>
            <input
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              required
              className="w-full border rounded px-3 py-2 text-sm"
            />
          </div>
          <div>
            <label className="block text-sm font-medium mb-1">설명</label>
            <textarea
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              className="w-full border rounded px-3 py-2 text-sm"
              rows={3}
            />
          </div>
          <div>
            <label className="block text-sm font-medium mb-1">
              이미지 URL
            </label>
            <input
              type="url"
              value={imageUrl}
              onChange={(e) => setImageUrl(e.target.value)}
              className="w-full border rounded px-3 py-2 text-sm"
            />
          </div>
          <div>
            <label className="block text-sm font-medium mb-1">
              클릭 URL *
            </label>
            <input
              type="url"
              value={clickUrl}
              onChange={(e) => setClickUrl(e.target.value)}
              required
              className="w-full border rounded px-3 py-2 text-sm"
            />
          </div>
          <div className="flex gap-2 mt-2">
            <button
              type="submit"
              className="px-4 py-2 bg-blue-600 text-white text-sm rounded hover:bg-blue-700"
            >
              저장
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

      {title && (
        <div className="w-80">
          <h2 className="text-sm font-medium text-gray-500 mb-3">미리보기</h2>
          <CreativePreview
            creative={{
              id: 0,
              adGroupId: 0,
              title,
              description: description || null,
              imageUrl: imageUrl || null,
              clickUrl: clickUrl || "#",
              status: "DRAFT",
            }}
          />
        </div>
      )}
    </div>
  );
}
