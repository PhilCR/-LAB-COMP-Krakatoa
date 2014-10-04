for %%a in (ok*.kra) do awk -f extract.awk %%a >> ok-vector.txt

for %%a in (er*.kra) do awk -f extract.awk %%a >> er-vector.txt
