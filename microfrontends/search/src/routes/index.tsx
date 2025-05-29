import { Search } from '@/components/Search'
import { Listings } from '@/components/Listings'
import { createFileRoute } from '@tanstack/react-router'

export const Route = createFileRoute('/')({
  component: App,
})

function App() {
  return (
    <div>
      <Search />
      <Listings />
    </div>
  )
}
