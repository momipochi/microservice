import { Header } from '@/components/Header'
import { createFileRoute, Outlet } from '@tanstack/react-router'

export const Route = createFileRoute('/_searchLayout')({
  component: LayoutComponent,
})

function LayoutComponent() {
  return (
    <div>
      <Header />
      <main className="max-w-[1248px] mx-auto px-4">
        <Outlet />
      </main>
    </div>
  )
}
