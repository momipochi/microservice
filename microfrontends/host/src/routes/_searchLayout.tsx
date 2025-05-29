import { createFileRoute, Outlet } from '@tanstack/react-router'
import { lazy } from 'react'

export const Route = createFileRoute('/_searchLayout')({
  component: LayoutComponent,
})

const Search = lazy(() =>
  import('searchRemote/Search').then((mod) => ({
    default: mod.default?.Search || mod.Search,
  })),
)

function LayoutComponent() {
  return (
    <div>
      <Search />
      <Outlet></Outlet>
    </div>
  )
}
