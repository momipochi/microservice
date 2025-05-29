import { createFileRoute } from '@tanstack/react-router'
import { lazy, Suspense } from 'react'

export const Route = createFileRoute('/')({
  component: App,
})
const Search = lazy(() =>
  import('searchRemote/Search').then((mod) => {
    console.log('The content of mod is: ', mod)
    return {
      default: mod.default?.Search || mod.Search,
    }
  }),
)

function App() {
  return (
    <div>
      <Suspense fallback={<div>Loading search component...</div>}>
        <Search />
      </Suspense>
    </div>
  )
}
