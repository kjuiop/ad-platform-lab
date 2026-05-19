export default function Loading() {
  return (
    <div className="animate-pulse">
      <div className="h-7 bg-gray-200 rounded w-48 mb-2" />
      <div className="h-4 bg-gray-100 rounded w-32 mb-6" />
      <div className="space-y-3 mt-8">
        {[1, 2].map((i) => (
          <div key={i} className="h-12 bg-gray-100 rounded" />
        ))}
      </div>
    </div>
  );
}
