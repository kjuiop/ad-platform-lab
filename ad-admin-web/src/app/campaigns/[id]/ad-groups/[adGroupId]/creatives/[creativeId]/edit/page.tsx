"use client";

import { useRouter, useParams } from "next/navigation";
import { useState, useEffect } from "react";
import { Creative } from "@/types/ad";
import { CreativePreview } from "@/components/CreativePreview";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Textarea } from "@/components/ui/textarea";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";

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
    router.push(`/campaigns/${params.id}/ad-groups/${params.adGroupId}`);
  }

  if (loading) {
    return <p className="text-muted-foreground text-sm">로딩 중...</p>;
  }

  return (
    <div className="flex gap-8">
      <Card className="max-w-lg flex-1">
        <CardHeader>
          <CardTitle>소재 수정</CardTitle>
        </CardHeader>
        <CardContent>
          {error && <p className="text-destructive text-sm mb-4">{error}</p>}
          <form onSubmit={handleSubmit} className="flex flex-col gap-4">
            <div className="space-y-2">
              <Label htmlFor="title">제목 *</Label>
              <Input
                id="title"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                required
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="description">설명</Label>
              <Textarea
                id="description"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                rows={3}
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="imageUrl">이미지 URL</Label>
              <Input
                id="imageUrl"
                type="url"
                value={imageUrl}
                onChange={(e) => setImageUrl(e.target.value)}
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="clickUrl">클릭 URL *</Label>
              <Input
                id="clickUrl"
                type="url"
                value={clickUrl}
                onChange={(e) => setClickUrl(e.target.value)}
                required
              />
            </div>
            <div className="flex gap-2 pt-2">
              <Button type="submit">저장</Button>
              <Button type="button" variant="outline" onClick={() => router.back()}>
                취소
              </Button>
            </div>
          </form>
        </CardContent>
      </Card>

      {title && (
        <div className="w-80">
          <p className="text-sm font-medium text-muted-foreground mb-3">
            미리보기
          </p>
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
