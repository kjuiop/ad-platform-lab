"use client";

interface AdSlotProps {
  slotId: string;
  className?: string;
}

export function AdSlot({ slotId, className }: AdSlotProps) {
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
