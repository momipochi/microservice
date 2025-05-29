import { createFileRoute } from '@tanstack/react-router'
import { lazy, Suspense } from 'react'

export const Route = createFileRoute('/listing')({
  component: Listing,
})

const Listings = lazy(() =>
  import('searchRemote/Listings').then((mod) => ({
    default: mod.default?.Listings || mod.Listings,
  })),
)

function Listing() {
  return (
    <Suspense fallback={<div>Loading listings component...</div>}>
      <Listings />
    </Suspense>
  )
}
