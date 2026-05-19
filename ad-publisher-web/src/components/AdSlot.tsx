"use client";

import { useEffect, useState } from "react";
import { AdResponse } from "@/types/ad";

interface AdSlotProps {
  slotId: string;
  className?: string;
}

export function AdSlot({ slotId, className }: AdSlotProps) {
  const [ad, setAd] = useState<AdResponse | null>(null);

  useEffect(() => {
    fetch(`/api/ads?slotId=${slotId}`)
      .then((res) => res.json())
      .then(setAd)
      .catch(() => setAd(null));
  }, [slotId]);

  if (!ad) {
    return (
      <div
        data-slot-id={slotId}
        className={`border-2 border-dashed border-gray-300 bg-gray-50
                    flex items-center justify-center text-gray-400 text-sm
                    ${className ?? ""}`}
      >
        광고 영역 ({slotId})
      </div>
    );
  }

  return (
    <div
      data-slot-id={slotId}
      className={`relative border border-gray-100 bg-gradient-to-r from-gray-50 to-white p-4 rounded-lg ${className ?? ""}`}
    >
      <span className="absolute top-2 right-2 px-1.5 py-0.5 text-[10px] font-medium text-blue-400 bg-blue-50 rounded">
        광고
      </span>
      <a href={ad.clickUrl} className="block pr-8">
        <p className="font-semibold text-sm text-gray-800">{ad.title}</p>
        <p className="text-gray-500 text-xs mt-1 leading-relaxed">
          {ad.description}
        </p>
      </a>
    </div>
  );
}
