// routes/listing.tsx
import { createFileRoute } from '@tanstack/react-router'
import { lazy, Suspense } from 'react'

export const Route = createFileRoute('/_searchLayout/listing')({
  component: Listing,
})

const Listings = lazy(() =>
  import('searchRemote/Listings').then((mod) => ({
    default: mod.default?.Listings || mod.Listings,
  })),
)

function Listing() {
  return (
    <div>
      <Suspense fallback={<div>Loading listings...</div>}>
        <Listings />
      </Suspense>
    </div>
  )
}
