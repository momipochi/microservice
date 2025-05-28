import { createFileRoute } from '@tanstack/react-router'
import { Listings } from '@/components/Listings'

export const Route = createFileRoute('/')({
  component: App,
})

function App() {
  return <Listings />
}
